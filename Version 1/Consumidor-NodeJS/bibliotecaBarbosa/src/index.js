const express = require("express");
const iniciarConsumidor = require("./consumers/libro.consumer");
const libroRoutes = require("./routes/libro.routes");

const app = express();
app.use(express.json());
app.use("/api/libros", libroRoutes);

const PORT = process.env.PORT || 3000;
app.listen(PORT, async () => {
    console.log(`🚀 Servidor escuchando en el puerto ${PORT}`);
    await iniciarConsumidor();
});
