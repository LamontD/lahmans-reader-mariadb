/*
 * Copyright (C) 2020 lamontdozierjr
 *
 * This program is licensed under a Creative Commons Attribution-Share 
 * Alike 3.0 Unported License ("CC-BY-SA"). An explanation of CC-BY-SA can 
 * be found at:
 *
 * <http://creativecommons.org/license/by-sa/3.0/>.
 *
 * Unless required by applicable law or agreed to in writing, this software
 * is distributed on an "AS IS" BASIS, WITHOUT ANY WARRANTY or even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
package com.lamontd.lahmans.reader.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamontd.utils.jackson.JacksonMapper;
import com.lamontd.utils.model.MappedTransportObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author lamontdozierjr
 */
@Service
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "lahmans-updates";

    private static final Log logger = LogFactory.getLog(KafkaSender.class);

    public void sendMessage(Object messageObject) {
        try {
            final ObjectMapper outputMapper = JacksonMapper.getStandardMapper();
            MappedTransportObject transportObject = new MappedTransportObject(messageObject);
            String messageString = outputMapper.writeValueAsString(transportObject);
            kafkaTemplate.send(TOPIC, messageString);
        } catch (JsonProcessingException ex) {
            logger.warn("Problem trying to convert output message to topic", ex);
        }
    }

}
