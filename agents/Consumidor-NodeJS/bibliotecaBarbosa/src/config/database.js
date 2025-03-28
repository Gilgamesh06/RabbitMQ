const { Sequelize } = require("sequelize");
require("dotenv").config();

const sequelize = new Sequelize(process.env.DB_NAME, process.env.DB_USER, process.env.DB_PASS, {
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    dialect: "postgres",
    logging: false,
});

const testConnection = async () => {
    try {
        await sequelize.authenticate();
        console.log("✅ Conexión con PostgreSQL establecida.");
    } catch (error) {
        console.error("❌ Error al conectar con PostgreSQL:", error.message);
    }
};

testConnection();

module.exports = sequelize;
