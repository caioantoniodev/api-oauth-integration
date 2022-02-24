package com.poc.requestoauth.adapters.in;

import com.poc.requestoauth.adapters.out.CharacterExternalService;
import com.poc.requestoauth.domain.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/characters")
public class CharactersController {

    @Autowired
    CharacterExternalService characterExternalService;

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getUsers(@PathVariable String id) {
        Character response = characterExternalService.findOneCharacter(id);

        return ResponseEntity.ok(response);
    }
}
