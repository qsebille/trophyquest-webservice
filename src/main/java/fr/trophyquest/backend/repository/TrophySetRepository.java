package fr.trophyquest.backend.repository;

import fr.trophyquest.backend.domain.entity.TrophySet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrophySetRepository extends JpaRepository<TrophySet, UUID> {

    @Query("""
                select distinct g.id
                from TrophySet g
                where exists (
                    select 1
                    from IgdbCandidate c
                    where c.trophySet = g and c.status = 'PENDING'
                )
                order by g.id
            """)
    Page<UUID> findTrophySetIdsHavingPendingCandidate(Pageable pageable);

    @Query("""
                select distinct g
                from TrophySet g
                join fetch g.igdbCandidates c
                join fetch c.candidate ig
                where g.id in :ids and c.status = 'PENDING'
                order by g.id
            """)
    List<TrophySet> fetchTrophySetsWithCandidatesByIds(@Param("ids") List<UUID> ids);
}
