package fr.aelion.streamer.services;

import fr.aelion.streamer.dto.AddMemberDto;
import fr.aelion.streamer.dto.FullCourseDto;
import fr.aelion.streamer.dto.SimpleMemberDto;
import fr.aelion.streamer.dto.SimpleMemberProjection;
import fr.aelion.streamer.dto.simplerDtos.*;
import fr.aelion.streamer.entities.Member;
import fr.aelion.streamer.enumFolder.MemberType;
import fr.aelion.streamer.repositories.MemberRepository;
import fr.aelion.streamer.services.exceptions.EmailAlreadyExistsException;
import fr.aelion.streamer.services.exceptions.LoginAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@Service
public class MemberService {
    @Autowired
    private MemberRepository repository;
    @Autowired
    private ConvertDtoService convertDtoService;

    @Autowired
    private ModelMapper modelMapper;

    public List<MemberDto> findAll() {
        List<Member> members = repository.findAll();
        List<MemberDto> memberDtos = members.stream().map(s -> {
            MemberDto memberDto = modelMapper.map(s, MemberDto.class);
            return memberDto;
        }).toList();
        return memberDtos;
    }

    public MemberDto findById(int id) {
        return repository.findById(id)
              .map(s -> {
                    MemberDto memberDto = modelMapper.map(s, MemberDto.class);
                    return memberDto;
                })
              .orElse(null);
    }

    public List<SimpleMemberDto> findSimpleMembers() {
        return repository.findAll()
                .stream()
                .map(s -> {
                    SimpleMemberDto dto = new SimpleMemberDto();
                    dto.setId(s.getId());
                    dto.setLastName(s.getLastName());
                    dto.setFirstName(s.getFirstName());
                    dto.setEmail(s.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<SimpleMemberProjection> fromProjection() {
        return repository.getSimpleMembers();
    }

    public Member add(AddMemberDto member) throws Exception {
        Member anyMember = repository.findByEmail(member.getEmail());
        if (anyMember != null) {
            throw new EmailAlreadyExistsException("Email " + member.getEmail() + " already exists");
        }
        anyMember = repository.findByLogin(member.getLogin());
        if (anyMember != null) {
            throw new LoginAlreadyExistsException("Login " + member.getLogin() + " already exists");
        }
        Member newMember = modelMapper.map(member, Member.class);
        newMember.setRole(MemberType.STUDENT);
        newMember = (Member) repository.save(newMember);

        return newMember;
    }

    public void update(Member member) throws Exception {
        try {
            repository.save(member);
        } catch (Exception e) {
            throw new Exception("Something went wrong while updating Member");
        }
    }

    public ConceptorDto findOne(int id) {
        return repository.findById(id)
                .map(s -> {
                    ConceptorDto conceptorDto = modelMapper.map(s, ConceptorDto.class);
                    List<CourseDto> courseDtos  = s.getCourses().stream().map((c) -> {
                        return convertDtoService.convertCourseToDto(c);
                    }).toList();
                    List<ModuleDto> moduleDtos  = convertDtoService.convertModulesListToDto(s.getModules());

                    List<MediaDto> mediasDtos  = s.getMedias().stream().map((m) -> {
                        return modelMapper.map(m, MediaDto.class);
                    }).toList();
                    conceptorDto.setCourses(courseDtos);
                    conceptorDto.setModules(moduleDtos);
                    conceptorDto.setMedias(mediasDtos);
                    return conceptorDto;
                })
                .orElseThrow();
    }


    public void delete(int id) {
        try {
            var member = this.findOne(id);
            repository.delete(modelMapper.map(member, Member.class));
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    public Set<Integer> multipleDelete(Set<Integer> ids) {
        var nonDeletedIds = new HashSet<Integer>();
        ids.stream()
                .forEach(i -> {
                    try {
                        repository.delete(modelMapper.map(findOne(i), Member.class));
                    } catch (NoSuchElementException e) {
                        nonDeletedIds.add(i);
                    } catch (Exception e) {
                        nonDeletedIds.add(i);
                    }
                });
        return nonDeletedIds;
    }

    @CrossOrigin
    public Optional<MemberDto> findByLoginAndPasswordSimpler(String login, String password) {
        return memberToMemberDto(repository.findByLoginAndPassword(login, password));
    }

    private Optional<MemberDto> memberToMemberDto(Optional<Member> member) {
        if (member.isEmpty()) {
            return Optional.empty();
        }
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.get().getId());
        memberDto.setPassword(member.get().getPassword());
        memberDto.setLogin(member.get().getLogin());
        memberDto.setFirstName(member.get().getFirstName());
        memberDto.setLastName(member.get().getLastName());
        memberDto.setPhoneNumber(member.get().getPhoneNumber());
        memberDto.setEmail(member.get().getEmail());
        memberDto.setRole(member.get().getRole());
        return Optional.of(memberDto);
    }

    public String recovery(String login, String email) {
        Optional<Member> memberOpt = repository.findByLoginAndEmail(login, email);
        if (!memberOpt.isEmpty()) {
            String password = passwordGenerator();
            Member member = memberOpt.get();
            member.setPassword(password);
            repository.save(member);
            return password;
        }
        return null;
    }

    private String passwordGenerator() {
        SecureRandom randomGen = new SecureRandom();
        randomGen.setSeed(randomGen.generateSeed(8));
        StringBuilder res = new StringBuilder();
        while (res.length() < 10) {
            Character c = Character.valueOf((char) (randomGen.nextInt() % 26 + 65));
            if (Character.isLetterOrDigit(c)) {
                res.append(c);
            }
        }
        return res.toString();
    }
}
