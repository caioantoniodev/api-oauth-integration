package com.workzone.apioauthintegration.adapter.in;

import com.workzone.apioauthintegration.application.PetStoreService;
import com.workzone.apioauthintegration.domain.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet-store/v1/pets")
public class PetStoreController {

    private final PetStoreService petStoreService;

    public PetStoreController(PetStoreService petStoreService) {
        this.petStoreService = petStoreService;
    }

    @GetMapping
    public ResponseEntity<Pet> getPet() {

        return ResponseEntity.status(HttpStatus.OK).body(petStoreService.getPet());
    }
}
