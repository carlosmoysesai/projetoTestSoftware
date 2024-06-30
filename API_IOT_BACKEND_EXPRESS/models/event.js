// models/event.js
const mongoose = require('mongoose');

const eventSchema = new mongoose.Schema({
  person: {
    id: { type: Number, required: true },
    name: { type: String, required: true }
  },
  deviceId: { type: Number, required: true },
  description: { type: String, required: true },
  type: { type: String, required: true },
  timestamp: { type: Date, default: Date.now },
  additionalDetails: { type: String }
});

const Event = mongoose.model('Event', eventSchema);

module.exports = Event;
