package fr.aelion.streamer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.aelion.streamer.dto.AddMemberDto;
import fr.aelion.streamer.dto.SimpleMemberDto;
import fr.aelion.streamer.dto.SimpleMemberProjection;
import fr.aelion.streamer.dto.simplerDtos.MemberDto;
import fr.aelion.streamer.entities.Member;
import fr.aelion.streamer.services.MemberService;
import fr.aelion.streamer.services.exceptions.EmailAlreadyExistsException;
import fr.aelion.streamer.services.exceptions.LoginAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("api/v1/students") // http://127.0.0.1:5000/api/v1/students
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<MemberDto> findAll() {
        return memberService.findAll();
    }

    @GetMapping("{id}") // GET http://127.0.0.1:5000/api/v1/students/1
    public ResponseEntity<?> findOne(@PathVariable int id) {
        try {
            return ResponseEntity.ok(memberService.findOne(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Member with " + id + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("simple")
    public List<SimpleMemberProjection> findSimpleMembers() {
        return memberService.fromProjection();
    }

    @GetMapping("dto")
    public List<SimpleMemberDto> simpleMemberDtos() {
        return memberService.findSimpleMembers();
    }

    /**
     * POST a new student
     * uri : POST http://127.0.0.1:5000/api/v1/students
     *
     * @param member
     * @return
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody AddMemberDto member) {
        try {
            Member newMember = memberService.add(member);
            return ResponseEntity.created(null).body(newMember);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.reject());
        } catch (LoginAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body((e.reject()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> update(@RequestBody Member member) {
        try {
            memberService.update(member);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> singleDelete(@PathVariable int id) {
        try {
            memberService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> multipleDelete(@RequestBody Set<Integer> ids) {
        return ResponseEntity.ok(memberService.multipleDelete(ids));
    }


    @PostMapping("byLoginAndPassword")
    public ResponseEntity<?> findByLoginAndPassword(@RequestBody Member Member) {
        return this.memberService.findByLoginAndPasswordSimpler(Member.getLogin(), Member.getPassword())
                .map(u -> {
                    return ResponseEntity.ok(u);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("recovery")
    public ResponseEntity<?> passwordRecovery(@RequestBody Member Member) throws IOException {
        String response = (this.memberService.recovery(Member.getLogin(), Member.getEmail()));
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return ResponseEntity.ok(objectMapper.writeValueAsString(response));
    }
}
