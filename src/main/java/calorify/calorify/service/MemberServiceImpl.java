package calorify.calorify.service;

import calorify.calorify.domain.Meal;
import calorify.calorify.domain.Member;
import calorify.calorify.dto.MemberForm;
import calorify.calorify.dto.MemberInfoDTO;
import calorify.calorify.dto.MemberInfoModifyDataDTO;
import calorify.calorify.repository.MealRepository;
import calorify.calorify.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Map<String, String> save(MemberForm memberForm) {
        Member member = new Member().formToMember(memberForm);
        Member saved = memberRepository.save(member);
        return Map.of("success", saved.getMemId());
    }

    @Override
    public MemberInfoDTO getMemberInfo(String memId) {
        Member member = memberRepository.findById(memId).orElse(null);
        return member.MemberToInfo();
    }

    @Override
    public void modifyMember(String memId, MemberInfoModifyDataDTO memberInfoDTO) {
        Member member = memberRepository.findById(memId).orElse(null);
        member.memberModifyInfo(memberInfoDTO);
        memberRepository.save(member);
    }

    public Member getOne(String memId) {
        Optional<Member> memberOptional = memberRepository.findById(memId);
        return memberOptional.orElse(null);
    }

    @Override
    public Boolean nameIsDuplicate(String name) {
        Optional<Member> id = memberRepository.findById(name);
        return id.isPresent();
    }

}
