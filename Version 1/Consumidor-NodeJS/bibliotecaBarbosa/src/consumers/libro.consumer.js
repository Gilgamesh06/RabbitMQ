const amqp = require("amqplib");
const LibroController = require("../controllers/libro.controller");
require("dotenv").config();

async function iniciarConsumidor() {
    try {
        const connection = await amqp.connect(process.env.RABBITMQ_URL);
        const channel = await connection.createChannel();
        await channel.assertExchange(process.env.EXCHANGE_NAME, "direct", { durable: true });

        // Agregar libro
        await channel.assertQueue(process.env.ADD_QUEUE_NAME);
        channel.bindQueue(process.env.ADD_QUEUE_NAME, process.env.EXCHANGE_NAME, process.env.ADD_ROUTING_KEY);
        channel.consume(process.env.ADD_QUEUE_NAME, msg => {
            const libro = JSON.parse(msg.content.toString());
            LibroController.agregarLibro(libro);
            channel.ack(msg);
        });

        // Actualizar libro
        await channel.assertQueue(process.env.UPDATE_QUEUE_NAME);
        channel.bindQueue(process.env.UPDATE_QUEUE_NAME, process.env.EXCHANGE_NAME, process.env.UPDATE_ROUTING_KEY);
        channel.consume(process.env.UPDATE_QUEUE_NAME, msg => {
            const libro = JSON.parse(msg.content.toString());
            LibroController.actualizarLibro(libro);
            channel.ack(msg);
        });

        // Eliminar libro
        await channel.assertQueue(process.env.DELETE_QUEUE_NAME);
        channel.bindQueue(process.env.DELETE_QUEUE_NAME, process.env.EXCHANGE_NAME, process.env.DELETE_ROUTING_KEY);
        channel.consume(process.env.DELETE_QUEUE_NAME, msg => {
            const { titulo } = JSON.parse(msg.content.toString());
            LibroController.eliminarLibro(titulo);
            channel.ack(msg);
        });

        console.log("✅ Consumidor en RabbitMQ listo!");
    } catch (error) {
        console.error("❌ Error en el consumidor de RabbitMQ:", error.message);
    }
}

module.exports = iniciarConsumidor;
