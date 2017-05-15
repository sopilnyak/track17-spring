package track.container;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import track.container.config.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;


public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;

        try {
            rootNode = objectMapper.readTree(configFile);
        } catch (IOException e) {
            throw new InvalidConfigurationException("File not found");
        }

        if (!rootNode.has("beans")) {
            throw new InvalidConfigurationException("No \"beans\", field");
        }

        List<Bean> beanList = new ArrayList();

        for (JsonNode bean: rootNode.get("beans")) {
            beanList.add(parseBean(bean));
        }

        return beanList;
    }

    private Property parseProperty(JsonNode property) throws InvalidConfigurationException {

        if (!property.has("name")) {
            throw new InvalidConfigurationException("No \"name\" field");
        }

        if (!(property.has("type"))) {
            throw new InvalidConfigurationException("No \"type\" field");
        }

        if (!(property.has("value"))) {
            throw new InvalidConfigurationException("No \"type\" field");
        }

        String name = property.get("name").asText();

        ValueType type;
        String value = property.get("value").asText();

        if (property.get("type").asText().equals("REF")) {
            type = ValueType.REF;
        } else {
            type = ValueType.VAL;
        }

        return new Property(name, value, type);
    }

    private Bean parseBean(JsonNode bean) throws InvalidConfigurationException {
        if (!bean.has("id")) {
            throw new InvalidConfigurationException("No \"id\" field");
        }

        if (!bean.has("className")) {
            throw new InvalidConfigurationException("No \"className\" field");
        }

        if (!bean.has("properties")) {
            throw new InvalidConfigurationException("No \"properties\" field");
        }

        String id = bean.get("id").asText();
        String className = bean.get("className").asText();
        Map<String, Property> properties = new HashMap();

        for (JsonNode property: bean.get("properties")) {
            Property parsedProperty = parseProperty(property);
            properties.put(parsedProperty.getName(), parsedProperty);
        }

        return new Bean(id, className, properties);
    }
}
