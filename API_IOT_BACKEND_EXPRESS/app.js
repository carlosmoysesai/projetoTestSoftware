const express = require('express');
const dotenv = require('dotenv');
const admin = require('firebase-admin');
const eventRoutes = require('./routes/eventRoutes');

// Carregar configurações do .env
dotenv.config();

// Inicializar Firebase Admin SDK
const serviceAccount = require('./config/serviceAccountKey.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

const app = express();
const PORT = process.env.PORT || 3001;

// Middleware
app.use(express.json());

// Disponibiliza o Firestore no request
app.use((req, res, next) => {
  req.db = db;
  next();
});

// Rotas
app.use('/api/events', eventRoutes);

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
