const amqp = require("amqplib");
const LibroController = require("../controllers/libro.controllers");
require("dotenv").config();

async function iniciarConsumidor() {
    try {
        const connection = await amqp.connect(process.env.RABBITMQ_URL);
        const channel = await connection.createChannel();
        await channel.assertExchange(process.env.EXCHANGE_NAME, "direct", { durable: true });

        // Agregar libro
        await channel.assertQueue(process.env.ADD_QUEUE_NAME);
        // Vincula la cola con ambas claves: la propia y la común
        channel.bindQueue(process.env.ADD_QUEUE_NAME, process.env.EXCHANGE_NAME, process.env.ADD_ROUTING_KEY);
        channel.bindQueue(process.env.ADD_QUEUE_NAME, process.env.EXCHANGE_NAME, process.env.ADD_COMMON_ROUTING_KEY);
        channel.consume(process.env.ADD_QUEUE_NAME, msg => {
            const libro = JSON.parse(msg.content.toString());
            LibroController.agregarLibro(libro);
            channel.ack(msg);
        });

        // Actualizar libro
        await channel.assertQueue(process.env.UPDATE_QUEUE_NAME);
        // Vincula la cola con ambas claves para actualización
        channel.bindQueue(process.env.UPDATE_QUEUE_NAME, process.env.EXCHANGE_NAME, process.env.UPDATE_ROUTING_KEY);
        channel.bindQueue(process.env.UPDATE_QUEUE_NAME, process.env.EXCHANGE_NAME, process.env.UPDATE_COMMON_ROUTING_KEY);
        channel.consume(process.env.UPDATE_QUEUE_NAME, msg => {
            const libro = JSON.parse(msg.content.toString());
            LibroController.actualizarLibro(libro);
            channel.ack(msg);
        });

        // Eliminar libro
        await channel.assertQueue(process.env.DELETE_QUEUE_NAME);
        // Vincula la cola con ambas claves para eliminación
        channel.bindQueue(process.env.DELETE_QUEUE_NAME, process.env.EXCHANGE_NAME, process.env.DELETE_ROUTING_KEY);
        channel.bindQueue(process.env.DELETE_QUEUE_NAME, process.env.EXCHANGE_NAME, process.env.DELETE_COMMON_ROUTING_KEY);
        channel.consume(process.env.DELETE_QUEUE_NAME, msg => {
            const { titulo } = JSON.parse(msg.content.toString());
            LibroController.eliminarLibro(titulo);
            channel.ack(msg);
        });

        console.log("✅ Consumidor en RabbitMQ listo!");
    } catch (error) {
        console.error("❌ Error en el consumidor de RabbitMQ:", error.message);
        // Espera 60 segundos y vuelve a intentarlo
        setTimeout(() => {
            console.log("⏳ Reintentando conexión a RabbitMQ...");
            iniciarConsumidor();
        }, 60000);
    }
}

module.exports = iniciarConsumidor;
