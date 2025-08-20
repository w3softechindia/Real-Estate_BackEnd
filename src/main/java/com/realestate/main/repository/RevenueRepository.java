package com.realestate.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.realestate.main.entity.Revenue;
import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long>{

	@Query("SELECT SUM(r.revenue) FROM Revenue r WHERE r.agent.id = :agentId")
	Double getTotalRevenueByAgent(@Param("agentId") int agentId);
	
	@Query("SELECT MONTH(r.transactionDate), YEAR(r.transactionDate), SUM(r.revenue) " +
		       "FROM Revenue r " +
		       "WHERE r.id = :agentId " +
		       "GROUP BY YEAR(r.transactionDate), MONTH(r.transactionDate) " +
		       "ORDER BY YEAR(r.transactionDate), MONTH(r.transactionDate)")
	List<Object[]> getMonthlyRevenueByAgent(@Param("agentId") int agentId);
}
