package com.market0913.domain.repository;

import com.market0913.domain.model.member.Member;
import com.market0913.domain.model.member.MemberType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberIdAndType(String memberId, MemberType type);
}
