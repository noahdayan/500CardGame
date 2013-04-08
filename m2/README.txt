We created Player, so we could store information about each player:
	> This makes it easy to access various information, for instance the partner of a player
	> Also it means it was easy to implement an ordering system such that
		the winner of the last trick leads and the order is maintained from there.
	> It means we can easily compute the number of tricks won by each player and thus the team.
		>> we thought about putting the number of tricks won in Team, but then we would lose 
			potential functionality, and it would be harder to implement individual player stats
				e.g. if we wanted to compute which player on a team is better, etc.
	> Creating a player class means we can create an array of players and easily iterate through.
		This means it is much easier to follow, rather than if we used individual arrays for
			hands and scores and use indices instead.
	> The Player class makes it very easy to implement the Team class.
	
We created Team, so we could store information about the two teams:
	> Although Team is a small class and it would be relatively easy to include everything
		necessary in Player, the addition of Team makes it much easier to code
	> It makes it very easy to track score without having to fish points from both players.
	> It makes it easy to do statistics with Team, because of getScore and getPlayers

We modified GameEngine to randomize the order in which the AI strategies would play/bid/deal:
	> This helped us find and fix a lot more errors than previously, especially since looping through
		the games in Driver would make sure that all possible game states have been tried.

We used the iterative mode a lot during preliminary testing:
	> This helped us discover a lot of hidden errors, as they woudln't stop the game from carrying on,
		although not respecting the rules of the game
		
We added getters for testing purposes:
	> This added us in allowing us to access the private variables in game engine in order to properly
		test the randomness of the AI with it's many different choices.
	> This also makes it very easy to test using system.out prints to troubleshoot as we were able to
		directly access the real value of each of the variables and were not stuck guessing.

We were unable to test some of the method due to the randomness of the outcomes:
	> Methods such as bid cannot have their own test classes as we are unable to predict the outcome of
		random AI. We therefore needed to incorporate it into our other test cases.
	> We could not test any of the player choices since we cannot directly incorporate user inputs into
		test cases.
	> To avoid unnecessary changes, we avoided using setters which could distort our data, this left
		methods such as allPasses impossible to test without setting the bid of each of the players.
	> We did not test any of the helper or print methods as these are not essential to the game engines
		processes.