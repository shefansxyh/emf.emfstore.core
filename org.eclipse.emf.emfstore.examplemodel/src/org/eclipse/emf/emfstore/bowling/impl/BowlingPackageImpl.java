/**
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.bowling.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.emfstore.bowling.Area;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Referee;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.bowling.TournamentType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BowlingPackageImpl extends EPackageImpl implements BowlingPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass playerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass leagueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tournamentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass matchupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gameEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass playerToPointsMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass refereeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass refereeToGamesMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass areaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum tournamentTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BowlingPackageImpl() {
		super(eNS_URI, BowlingFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link BowlingPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BowlingPackage init() {
		if (isInited) return (BowlingPackage)EPackage.Registry.INSTANCE.getEPackage(BowlingPackage.eNS_URI);

		// Obtain or create and register package
		BowlingPackageImpl theBowlingPackage = (BowlingPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BowlingPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BowlingPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theBowlingPackage.createPackageContents();

		// Initialize created meta-data
		theBowlingPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBowlingPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BowlingPackage.eNS_URI, theBowlingPackage);
		return theBowlingPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlayer() {
		return playerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayer_Name() {
		return (EAttribute)playerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayer_DateOfBirth() {
		return (EAttribute)playerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayer_Height() {
		return (EAttribute)playerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayer_IsProfessional() {
		return (EAttribute)playerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayer_EMails() {
		return (EAttribute)playerEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayer_NumberOfVictories() {
		return (EAttribute)playerEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayer_PlayedTournamentTypes() {
		return (EAttribute)playerEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLeague() {
		return leagueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLeague_Name() {
		return (EAttribute)leagueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLeague_Players() {
		return (EReference)leagueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTournament() {
		return tournamentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTournament_Matchups() {
		return (EReference)tournamentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTournament_Type() {
		return (EAttribute)tournamentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTournament_PlayerPoints() {
		return (EReference)tournamentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTournament_Players() {
		return (EReference)tournamentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTournament_Referees() {
		return (EReference)tournamentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTournament_PriceMoney() {
		return (EAttribute)tournamentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTournament_ReceivesTrophy() {
		return (EAttribute)tournamentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTournament_MatchDays() {
		return (EAttribute)tournamentEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMatchup() {
		return matchupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMatchup_Games() {
		return (EReference)matchupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGame() {
		return gameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGame_Matchup() {
		return (EReference)gameEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGame_Player() {
		return (EReference)gameEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGame_Frames() {
		return (EAttribute)gameEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlayerToPointsMap() {
		return playerToPointsMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlayerToPointsMap_Key() {
		return (EReference)playerToPointsMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayerToPointsMap_Value() {
		return (EAttribute)playerToPointsMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReferee() {
		return refereeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReferee_League() {
		return (EReference)refereeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRefereeToGamesMap() {
		return refereeToGamesMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRefereeToGamesMap_Key() {
		return (EReference)refereeToGamesMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRefereeToGamesMap_Value() {
		return (EReference)refereeToGamesMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArea() {
		return areaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArea_Areas() {
		return (EReference)areaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArea_Tournaments() {
		return (EReference)areaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTournamentType() {
		return tournamentTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BowlingFactory getBowlingFactory() {
		return (BowlingFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		playerEClass = createEClass(PLAYER);
		createEAttribute(playerEClass, PLAYER__NAME);
		createEAttribute(playerEClass, PLAYER__DATE_OF_BIRTH);
		createEAttribute(playerEClass, PLAYER__HEIGHT);
		createEAttribute(playerEClass, PLAYER__IS_PROFESSIONAL);
		createEAttribute(playerEClass, PLAYER__EMAILS);
		createEAttribute(playerEClass, PLAYER__NUMBER_OF_VICTORIES);
		createEAttribute(playerEClass, PLAYER__PLAYED_TOURNAMENT_TYPES);

		leagueEClass = createEClass(LEAGUE);
		createEAttribute(leagueEClass, LEAGUE__NAME);
		createEReference(leagueEClass, LEAGUE__PLAYERS);

		tournamentEClass = createEClass(TOURNAMENT);
		createEReference(tournamentEClass, TOURNAMENT__MATCHUPS);
		createEAttribute(tournamentEClass, TOURNAMENT__TYPE);
		createEReference(tournamentEClass, TOURNAMENT__PLAYER_POINTS);
		createEReference(tournamentEClass, TOURNAMENT__PLAYERS);
		createEReference(tournamentEClass, TOURNAMENT__REFEREES);
		createEAttribute(tournamentEClass, TOURNAMENT__PRICE_MONEY);
		createEAttribute(tournamentEClass, TOURNAMENT__RECEIVES_TROPHY);
		createEAttribute(tournamentEClass, TOURNAMENT__MATCH_DAYS);

		matchupEClass = createEClass(MATCHUP);
		createEReference(matchupEClass, MATCHUP__GAMES);

		gameEClass = createEClass(GAME);
		createEReference(gameEClass, GAME__MATCHUP);
		createEReference(gameEClass, GAME__PLAYER);
		createEAttribute(gameEClass, GAME__FRAMES);

		playerToPointsMapEClass = createEClass(PLAYER_TO_POINTS_MAP);
		createEReference(playerToPointsMapEClass, PLAYER_TO_POINTS_MAP__KEY);
		createEAttribute(playerToPointsMapEClass, PLAYER_TO_POINTS_MAP__VALUE);

		refereeEClass = createEClass(REFEREE);
		createEReference(refereeEClass, REFEREE__LEAGUE);

		refereeToGamesMapEClass = createEClass(REFEREE_TO_GAMES_MAP);
		createEReference(refereeToGamesMapEClass, REFEREE_TO_GAMES_MAP__KEY);
		createEReference(refereeToGamesMapEClass, REFEREE_TO_GAMES_MAP__VALUE);

		areaEClass = createEClass(AREA);
		createEReference(areaEClass, AREA__AREAS);
		createEReference(areaEClass, AREA__TOURNAMENTS);

		// Create enums
		tournamentTypeEEnum = createEEnum(TOURNAMENT_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(playerEClass, Player.class, "Player", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPlayer_Name(), ecorePackage.getEString(), "name", null, 0, 1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlayer_DateOfBirth(), ecorePackage.getEDate(), "dateOfBirth", null, 0, 1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlayer_Height(), ecorePackage.getEDouble(), "height", null, 0, 1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlayer_IsProfessional(), ecorePackage.getEBoolean(), "isProfessional", null, 0, 1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlayer_EMails(), ecorePackage.getEString(), "eMails", null, 0, -1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlayer_NumberOfVictories(), ecorePackage.getEInt(), "numberOfVictories", null, 0, 1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlayer_PlayedTournamentTypes(), this.getTournamentType(), "playedTournamentTypes", null, 0, -1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(leagueEClass, League.class, "League", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLeague_Name(), ecorePackage.getEString(), "name", null, 0, 1, League.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLeague_Players(), this.getPlayer(), null, "players", null, 0, -1, League.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tournamentEClass, Tournament.class, "Tournament", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTournament_Matchups(), this.getMatchup(), null, "matchups", null, 0, -1, Tournament.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTournament_Type(), this.getTournamentType(), "type", null, 0, 1, Tournament.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTournament_PlayerPoints(), this.getPlayerToPointsMap(), null, "playerPoints", null, 0, -1, Tournament.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTournament_Players(), this.getPlayer(), null, "players", null, 0, -1, Tournament.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTournament_Referees(), this.getRefereeToGamesMap(), null, "referees", null, 0, -1, Tournament.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTournament_PriceMoney(), ecorePackage.getEDouble(), "priceMoney", null, 0, -1, Tournament.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTournament_ReceivesTrophy(), ecorePackage.getEBoolean(), "receivesTrophy", null, 0, -1, Tournament.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTournament_MatchDays(), ecorePackage.getEDate(), "matchDays", null, 0, -1, Tournament.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(matchupEClass, Matchup.class, "Matchup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMatchup_Games(), this.getGame(), this.getGame_Matchup(), "games", null, 2, 2, Matchup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gameEClass, Game.class, "Game", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGame_Matchup(), this.getMatchup(), this.getMatchup_Games(), "matchup", null, 1, 1, Game.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGame_Player(), this.getPlayer(), null, "player", null, 0, 1, Game.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGame_Frames(), ecorePackage.getEInt(), "frames", null, 0, 10, Game.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(playerToPointsMapEClass, Map.Entry.class, "PlayerToPointsMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlayerToPointsMap_Key(), this.getPlayer(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlayerToPointsMap_Value(), ecorePackage.getEIntegerObject(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(refereeEClass, Referee.class, "Referee", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReferee_League(), this.getLeague(), null, "league", null, 0, 1, Referee.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(refereeToGamesMapEClass, Map.Entry.class, "RefereeToGamesMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRefereeToGamesMap_Key(), this.getReferee(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRefereeToGamesMap_Value(), this.getGame(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(areaEClass, Area.class, "Area", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArea_Areas(), this.getArea(), null, "areas", null, 0, -1, Area.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArea_Tournaments(), this.getTournament(), null, "tournaments", null, 0, -1, Area.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(tournamentTypeEEnum, TournamentType.class, "TournamentType");
		addEEnumLiteral(tournamentTypeEEnum, TournamentType.PRO);
		addEEnumLiteral(tournamentTypeEEnum, TournamentType.AMATEUR);

		// Create resource
		createResource(eNS_URI);
	}

} // BowlingPackageImpl