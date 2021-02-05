package com.MyPage.MyPage;

import com.MyPage.MyPage.Models.*;
import com.MyPage.MyPage.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class MyPageAboutBalancesApplication implements CommandLineRunner {

	@Autowired
	private UserRepository UR;

	@Autowired
	private SquadRepository SR;

	@Autowired
	private BalanceRepository BR;

	@Autowired
	private BalanceConfirmationRepository BCR;

	@Autowired
	private HistoryRepository HR;
	public static void main(String[] args) {
		SpringApplication.run(MyPageAboutBalancesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		 User user1;
		 user1 = UR.findById(8);

		 User user2;
		 user2 = UR.findById(9);

		 Squad squad;
		 squad = SR.findById(1);
		 /**
		 * Balance balance = new Balance(86.5f, user1, user2, squad);
		 * 		 BR.save(balance);
		 */

		/**
		 * BalanceConfirmation bc = new BalanceConfirmation(85.98f, "Za książki", new Date(), user1, user2);
		 * 		 BCR.save(bc);
		 */

		/**
		 * History history = new History("Imprezka", 50.50f, new Date(), user1, user2, squad);
		 * 		HR.save(history);
		 */

		Set<User> users = new HashSet<>();
		users.add(user1);
		users.add(user2);
		squad.setUsers(users);

		Set<Squad> squads = new HashSet<>();
		squads.add(squad);
		user1.setSquads(squads);
		user2.setSquads(squads);

		UR.save(user1);
		UR.save(user2);
		SR.save(squad);

		System.out.println(user1.getSquads());
		System.out.println(squad.getUsers());

	}
}
