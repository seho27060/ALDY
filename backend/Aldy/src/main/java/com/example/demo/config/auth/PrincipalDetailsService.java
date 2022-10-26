package com.example.demo.config.auth;

//@Service
//@RequiredArgsConstructor
//public class PrincipalDetailsService implements UserDetailsService {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String backjoonId) throws UsernameNotFoundException {
//        System.out.println("PrincipalDetailsService : 진입");
//        Optional<Member> member = memberRepository.findByBackjoonId(backjoonId);
//
//        return new PrincipalDetails(member.get());
//    }
//}
