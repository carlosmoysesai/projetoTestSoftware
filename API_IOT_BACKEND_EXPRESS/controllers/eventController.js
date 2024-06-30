// controllers/eventController.js

// criar vento
exports.createEvent = async (req, res) => {
  try {
    const newEvent = req.body;
    const docRef = await req.db.collection('events').add(newEvent);
    res.status(201).json({ id: docRef.id, ...newEvent });
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// pega eventos
exports.getAllEvents = async (req, res) => {
  try {
    const snapshot = await req.db.collection('events').get();
    const events = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
    res.status(200).json(events);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// pega unico evendo por id
exports.getEventById = async (req, res) => {
  try {
    const doc = await req.db.collection('events').doc(req.params.id).get();
    if (!doc.exists) {
      return res.status(404).json({ message: 'Event not found' });
    }
    res.status(200).json({ id: doc.id, ...doc.data() });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// atualiza
exports.updateEvent = async (req, res) => {
  try {
    const updatedEvent = req.body;
    const docRef = req.db.collection('events').doc(req.params.id);
    await docRef.update(updatedEvent);
    res.status(200).json({ id: req.params.id, ...updatedEvent });
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// Deleta
exports.deleteEvent = async (req, res) => {
  try {
    const docRef = req.db.collection('events').doc(req.params.id);
    await docRef.delete();
    res.status(200).json({ message: 'Event deleted successfully' });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};
