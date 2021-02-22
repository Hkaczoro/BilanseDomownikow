package com.MyPage.MyPage;

import com.MyPage.MyPage.Models.Balance;
import com.MyPage.MyPage.Models.BalanceConfirmation;
import com.MyPage.MyPage.Models.Squad;
import com.MyPage.MyPage.Models.User;
import com.MyPage.MyPage.Repositories.BalanceConfirmationRepository;
import com.MyPage.MyPage.Repositories.BalanceRepository;
import com.MyPage.MyPage.Repositories.SquadRepository;
import com.MyPage.MyPage.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.Serializable;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MyPageAboutBalancesApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SquadRepository squadRepository;

	@Autowired
	private BalanceConfirmationRepository balanceConfirmationRepository;

	@Autowired
	private BalanceRepository balanceRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void findUserByEmail(){
		String email = "check@email.com";

		User user = userRepository.findByEmail(email);
		assertThat(user).isNotNull();
	}

	@Test
	public  void findSquadByCode(){
		String code = "HeP6vY";
		Squad squad = squadRepository.findByCode("deded");

		assertThat(squad).isNull();
	}

	@Test
	public void findBalance(){

	}

}
