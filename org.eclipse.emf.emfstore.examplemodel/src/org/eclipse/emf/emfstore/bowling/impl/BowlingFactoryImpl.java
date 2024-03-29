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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.emfstore.bowling.*;
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
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BowlingFactoryImpl extends EFactoryImpl implements BowlingFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BowlingFactory init() {
		try {
			BowlingFactory theBowlingFactory = (BowlingFactory)EPackage.Registry.INSTANCE.getEFactory("http://org/eclipse/example/bowling"); 
			if (theBowlingFactory != null) {
				return theBowlingFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BowlingFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BowlingFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case BowlingPackage.PLAYER: return createPlayer();
			case BowlingPackage.LEAGUE: return createLeague();
			case BowlingPackage.TOURNAMENT: return createTournament();
			case BowlingPackage.MATCHUP: return createMatchup();
			case BowlingPackage.GAME: return createGame();
			case BowlingPackage.PLAYER_TO_POINTS_MAP: return (EObject)createPlayerToPointsMap();
			case BowlingPackage.REFEREE: return createReferee();
			case BowlingPackage.REFEREE_TO_GAMES_MAP: return (EObject)createRefereeToGamesMap();
			case BowlingPackage.AREA: return createArea();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case BowlingPackage.TOURNAMENT_TYPE:
				return createTournamentTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case BowlingPackage.TOURNAMENT_TYPE:
				return convertTournamentTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Player createPlayer() {
		PlayerImpl player = new PlayerImpl();
		return player;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public League createLeague() {
		LeagueImpl league = new LeagueImpl();
		return league;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tournament createTournament() {
		TournamentImpl tournament = new TournamentImpl();
		return tournament;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Matchup createMatchup() {
		MatchupImpl matchup = new MatchupImpl();
		return matchup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Game createGame() {
		GameImpl game = new GameImpl();
		return game;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Player, Integer> createPlayerToPointsMap() {
		PlayerToPointsMapImpl playerToPointsMap = new PlayerToPointsMapImpl();
		return playerToPointsMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Referee createReferee() {
		RefereeImpl referee = new RefereeImpl();
		return referee;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Referee, Game> createRefereeToGamesMap() {
		RefereeToGamesMapImpl refereeToGamesMap = new RefereeToGamesMapImpl();
		return refereeToGamesMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Area createArea() {
		AreaImpl area = new AreaImpl();
		return area;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TournamentType createTournamentTypeFromString(EDataType eDataType, String initialValue) {
		TournamentType result = TournamentType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTournamentTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BowlingPackage getBowlingPackage() {
		return (BowlingPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BowlingPackage getPackage() {
		return BowlingPackage.eINSTANCE;
	}

} // BowlingFactoryImpl