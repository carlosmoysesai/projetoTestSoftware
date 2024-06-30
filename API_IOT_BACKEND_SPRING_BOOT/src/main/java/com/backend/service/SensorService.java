package com.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dto.SensorDTO;
import com.backend.exceptions.NotFoundException;
import com.backend.model.Sensor;
import com.backend.repository.SensorRepository;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public Sensor create(SensorDTO dto) {
        Sensor sensor = new Sensor();
        BeanUtils.copyProperties(dto, sensor);
        return sensorRepository.save(sensor);
    }

    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> getById(long id) {
        return sensorRepository.findById(id);
    }

    public Sensor update(long id, SensorDTO dto) throws NotFoundException {
        Optional<Sensor> optionalSensor = sensorRepository.findById(id);
        Sensor sensor = optionalSensor.orElseThrow(() -> new NotFoundException("Sensor não encontrado"));
        BeanUtils.copyProperties(dto, sensor, "id");
        return sensorRepository.save(sensor);
    }

    public void delete(long id) throws NotFoundException {
        if (!sensorRepository.existsById(id)) {
            throw new NotFoundException("Sensor não encontrado");
        }
        sensorRepository.deleteById(id);
    }
}