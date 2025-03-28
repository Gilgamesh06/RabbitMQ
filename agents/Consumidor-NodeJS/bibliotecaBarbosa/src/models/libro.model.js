const { DataTypes } = require("sequelize");
const sequelize = require("../config/database");

const Libro = sequelize.define("Libro", {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
    },
    titulo: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    autor: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    descripcion: {
        type: DataTypes.TEXT,
    },
    fechaPublicacion: {
        type: DataTypes.DATE,
    },
}, {
    timestamps: false,
});

module.exports = Libro;
