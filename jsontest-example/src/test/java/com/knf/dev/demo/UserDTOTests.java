package com.knf.dev.demo;

import com.knf.dev.demo.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class UserDTOTests {


    @Autowired
    private JacksonTester<UserDTO> jacksonTester;

    @Value("classpath:user.json")
    Resource userResource;

    @Test
    void serializeInCorrectFormat() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("sibin@gmail.com");
        userDTO.setName("Sibin");

        LocalDate dob = LocalDate.of(1970, Month.JANUARY, 9);
        userDTO.setDob(dob);

        Map<String,String> roles= new HashMap<>();
        roles.put("role1","Admin");
        roles.put("role2","Editor");
        userDTO.setRoles(roles);

       JsonContent<UserDTO> json = jacksonTester.write(userDTO);

        // Assert against a `user.json` file
       assertThat(json).isEqualToJson(userResource);

        // JSON path based assertions
        assertThat(json).hasJsonPathStringValue("@.email");
        assertThat(json).extractingJsonPathStringValue("@.dob")
                .isEqualTo("1970-01-09");
        assertThat(json).extractingJsonPathStringValue("@.name")
                .isEqualTo("Sibin");
        assertThat(json).extractingJsonPathMapValue("@.roles").
                hasFieldOrProperty("role1");
        assertThat(json).extractingJsonPathMapValue("@.roles").
               extractingByKey("role1").isEqualTo("Admin");
    }

    @Test
    void deserializeFromCorrectFormat() throws Exception {

        //Convert Resource to String
        String json = StreamUtils.copyToString(userResource.getInputStream(),
                Charset.defaultCharset());

        UserDTO userDTO = jacksonTester.parseObject(json);

        assertThat(userDTO.getEmail()).isEqualTo("sibin@gmail.com");
        assertThat(userDTO.getName()).isEqualTo("Sibin");
        assertThat(userDTO.getDob()).isEqualTo("1970-01-09");
        assertThat(userDTO.getRoles().size()).isEqualTo(2);
    }
}
