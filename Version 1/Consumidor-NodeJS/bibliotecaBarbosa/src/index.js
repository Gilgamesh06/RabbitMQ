const express = require("express");
const sequelize = require("./config/database"); // Donde instancias Sequelize
const iniciarConsumidor = require("./consumers/libro.consumer");
const libroRoutes = require("./routes/libro.routes");

const app = express();
app.use(express.json());
app.use("/api/v1/biblioteca-barbosa", libroRoutes);

sequelize.sync()
  .then(() => {
    console.log("Tablas creadas/actualizadas en la base de datos.");
    app.listen(process.env.PORT || 3000, async () => {
      console.log(`Servidor escuchando en el puerto ${process.env.PORT || 3000}`);
      await iniciarConsumidor();
    });
  })
  .catch(error => {
    console.error("Error al sincronizar modelos:", error);
  });