package com.ftn.osa.projekat_osa.controller;

import com.ftn.osa.projekat_osa.android_dto.ContactDTO;
import com.ftn.osa.projekat_osa.android_dto.UserDTO;
import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongPasswordException;
import com.ftn.osa.projekat_osa.model.Contact;
import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.service.ContactService;
import com.ftn.osa.projekat_osa.service.UserService;
import com.ftn.osa.projekat_osa.service.serviceInterface.ContactServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactServiceInterface contactService;

    @PutMapping(value = "/{id}/changePassword")
    public ResponseEntity<Object> changePassword(@PathVariable(value = "id") Long id, @RequestBody Map<String, String> data) {
        //TODO odjaviti korisnika nakon uspesne promene lozinke
        if (data.containsKey("currentPassword") && data.containsKey("newPassword")) {
            try {
                if (userService.changePassword(id, data.get("currentPassword"), data.get("newPassword")).isPresent())
                    return new ResponseEntity<>(HttpStatus.OK);
                else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (WrongPasswordException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody Map<String, String> data) {
        try {
            Optional<User> userOptional = userService.updateUser(id, data);
            if (userOptional.isPresent()) return new ResponseEntity<>(new UserDTO(userOptional.get()), HttpStatus.OK);
            else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/contacts")
    public ResponseEntity<Set<ContactDTO>> getUserContacts(@PathVariable(value = "id") Long id){
        Set<Contact> contacts = contactService.getUsersContacts(id);
        return new ResponseEntity<>(contacts.stream().map(ContactDTO::new).collect(Collectors.toSet()), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/contacts", consumes = "application/json")
    public ResponseEntity<ContactDTO> addUserContact(@PathVariable(value = "id") Long id, @RequestBody ContactDTO contact){
        Contact contact1 = contactService.addUserContact(id, contact.getJpaEntity());
        return new ResponseEntity<>(new ContactDTO(contact1), HttpStatus.OK);
    }
}
