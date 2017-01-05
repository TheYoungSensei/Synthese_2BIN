DROP SCHEMA IF EXISTS reviProjet CASCADE;
CREATE SCHEMA reviProjet;

CREATE table reviProjet.utilisateurs(
	id_utilisateur serial PRIMARY KEY,
	nom varchar(255) NOT NULL CHECK(nom != ' ' AND nom != ''),
	prenom varchar(255) NOT NULL CHECK(prenom != ' ' AND prenom != ''),
	solde money NOT NULL CHECK(solde::money::numeric::float8 >= 0)
);

INSERT INTO reviProjet.utilisateurs VALUES(DEFAULT, 'Damas', 'Christophe', 2000);
INSERT INTO reviProjet.utilisateurs VALUES(DEFAULT, 'Grolaux', 'Donatien', 2000);
INSERT INTO reviProjet.utilisateurs VALUES(DEFAULT, 'Ferneeuw', 'Stéphanie', 2000);

CREATE TABLE reviProjet.comptes(
	id_compte serial PRIMARY KEY,
	num_compte char(10) NOT NULL CHECK(num_compte SIMILAR TO '[0-9]{4}-[0-9]{5}'),
	utilisateur integer NOT NULL REFERENCES reviProjet.utilisateurs(id_utilisateur),
	solde money NOT NULL CHECK (solde::money::numeric::float8 >= 0)
);

INSERT INTO reviProjet.comptes VALUES(DEFAULT, '1234-56789', 1,  2000);
INSERT INTO reviProjet.comptes VALUES(DEFAULT, '5632-12564', 2, 2000);
INSERT INTO reviProjet.Comptes VALUES(DEFAULT, '9876-87654', 1, 2000);
INSERT INTO reviProjet.comptes VALUES(DEFAULT, '7896-23565', 3, 2000);
INSERT INTO reviProjet.comptes VALUES(DEFAULT, '1236-02364', 2, 2000);

CREATE TABLE reviProjet.operations(
	id_operation serial PRIMARY KEY,
	compte_source integer NOT NULL REFERENCES reviProjet.comptes(id_compte),
	compte_destination integer NOT NULL REFERENCES reviProjet.comptes(id_compte) CHECK(compte_source != compte_destination),
	date_virement date NOT NULL CHECK(date_virement <= now()),
	montant money NOT NULL CHECK (montant::money::numeric::float8 >= 0)
);

CREATE TYPE reviProjet.evolution AS (compte_tiers char(10), nom_tiers varchar(255), prenom_tiers varchar(255), date_virement timestamp, montant money, solde money); 

CREATE OR REPLACE FUNCTION reviProjet.inserer_operation(varchar(255), varchar(255), char(10), varchar(255), varchar(255), char(10), timestamp, integer) RETURNS INTEGER AS $$
DECLARE
	nomSource ALIAS FOR $1;
	prenomSource ALIAS FOR $2;
	compteSource ALIAS FOR $3;
	nomDestination ALIAS FOR $4;
	prenomDestination ALIAS FOR $5;
	compteDestination ALIAS FOR $6;
	dateOp ALIAS FOR $7;
	montant ALIAS FOR $8;
	idCSource integer := 0;
	idCDestination integer := 0;
	id integer := 0;
BEGIN
	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c, reviProjet.utilisateurs ut WHERE c.num_compte = compteSource 
		AND lower(ut.nom) = lower(nomSource) AND lower(ut.prenom) = lower(prenomSource)) THEN
		RAISE foreign_key_violation;
	ELSE
		idCSource := (SELECT id_compte FROM reviProjet.comptes c WHERE c.num_compte = compteSource);
	END IF;

	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c, reviProjet.utilisateurs ut WHERE c.num_compte = compteDestination
	 AND lower(ut.nom) = lower(nomDestination) AND lower(ut.prenom) = lower(prenomDestination)) THEN
		RAISE foreign_key_violation;
	ELSE
		idCDestination := (SELECT id_compte FROM reviProjet.comptes c WHERE c.num_compte = compteDestination);
	END IF;

	INSERT INTO reviProjet.operations VALUES(DEFAULT, idCSource, idCDestination, dateOp, montant) RETURNING id_operation INTO id;
	RETURN id;

	EXCEPTION
		WHEN check_violation THEN RAISE EXCEPTION 'Erreur insertion operation';
END;
$$ LANGUAGE plpgsql;

SELECT reviProjet.inserer_operation('Damas', 'Christophe', '1234-56789', 'Grolaux', 'Donatien', '5632-12564','04/01/2017', 99);

CREATE OR REPLACE FUNCTION reviProjet.modifier_operation(varchar, varchar, char(10), varchar, varchar, char(10), timestamp, integer) RETURNS integer AS $$
DECLARE
	nomSource ALIAS FOR $1;
	prenomSource ALIAS FOR $2;
	compteSource ALIAS FOR $3;
	nomDestination ALIAS FOR $4;
	prenomDestination ALIAS FOR $5;
	compteDestination ALIAS FOR $6;
	dateOp ALIAS FOR $7;
	_montant ALIAS FOR $8;
	idCSource integer := 0;
	idCDestination integer := 0;
	idOp integer := 0;
BEGIN
	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c, reviProjet.utilisateurs ut WHERE c.num_compte = compteSource 
		AND lower(ut.nom) = lower(nomSource) AND lower(ut.prenom) = lower(prenomSource)) THEN
		RAISE foreign_key_violation;
	ELSE
		idCSource := (SELECT id_compte FROM reviProjet.comptes c WHERE c.num_compte = compteSource);
	END IF;

	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c, reviProjet.utilisateurs ut WHERE c.num_compte = compteDestination
	 AND lower(ut.nom) = lower(nomDestination) AND lower(ut.prenom) = lower(prenomDestination)) THEN
		RAISE foreign_key_violation;
	ELSE
		idCDestination := (SELECT id_compte FROM reviProjet.comptes c WHERE c.num_compte = compteDestination);
	END IF;

	IF 1 != (SELECT count(DISTINCT op.*) FROM reviProjet.operations op, reviProjet.comptes cs, reviProjet.comptes cd 
		WHERE op.compte_source = idCSource AND op.compte_destination = idCDestination AND op.date_virement = dateOp) THEN
		RAISE 'mauvaise operation';
	ELSE 
		idOp := (SELECT DISTINCT op.id_operation FROM reviProjet.operations op, reviProjet.comptes cs, reviProjet.comptes cd 
		WHERE op.compte_source = idCSource AND op.compte_destination = idCDestination AND op.date_virement = dateOp);
	END IF;

	UPDATE reviProjet.operations SET montant = _montant WHERE id_operation = idOp;

	RETURN idOp;

	EXCEPTION
		WHEN check_violation THEN RAISE 'Erreur modification operation';
END;
$$ LANGUAGE plpgsql;

SELECT reviProjet.modifier_operation('Damas', 'Christophe', '1234-56789', 'Grolaux', 'Donatien', '5632-12564','04/01/2017', 256);

CREATE OR REPLACE FUNCTION reviProjet.supprimer_operation(varchar, varchar, char(10), varchar, varchar, char(10), timestamp, integer) RETURNS integer AS $$
DECLARE
	_nomSource ALIAS FOR $1;
	_prenomSource ALIAS FOR $2;
	_compteSource ALIAS FOR $3;
	_nomDestination ALIAS FOR $4;
	_prenomDestination ALIAS FOR $5;
	_compteDestination ALIAS FOR $6;
	_dateOp ALIAS FOR $7;
	_montant ALIAS FOR $8;
	_idCSource integer := 0;
	_idCDestination integer := 0;
	_idOp integer := 0;
BEGIN
	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c, reviProjet.utilisateurs ut WHERE c.num_compte = _compteSource 
		AND lower(ut.nom) = lower(_nomSource) AND lower(ut.prenom) = lower(_prenomSource)) THEN
		RAISE foreign_key_violation;
	ELSE
		_idCSource := (SELECT id_compte FROM reviProjet.comptes c WHERE c.num_compte = _compteSource);
	END IF;

	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c, reviProjet.utilisateurs ut WHERE c.num_compte = _compteDestination
	 AND lower(ut.nom) = lower(_nomDestination) AND lower(ut.prenom) = lower(_prenomDestination)) THEN
		RAISE foreign_key_violation;
	ELSE
		_idCDestination := (SELECT id_compte FROM reviProjet.comptes c WHERE c.num_compte = _compteDestination);
	END IF;


	IF 1 != (SELECT count(DISTINCT op.*) FROM reviProjet.operations op, reviProjet.comptes cs, reviProjet.comptes cd 
		WHERE op.compte_source = _idCSource AND op.compte_destination = _idCDestination AND op.date_virement = _dateOp AND op.montant = cast(_montant AS money)) THEN
		RAISE 'mauvaise operation';
	ELSE 
		_idOp := (SELECT DISTINCT op.id_operation FROM reviProjet.operations op, reviProjet.comptes cs, reviProjet.comptes cd 
		WHERE op.compte_source = _idCSource AND op.compte_destination = _idCDestination AND op.date_virement = _dateOp AND op.montant = cast(_montant AS money));
	END IF;

	DELETE FROM reviProjet.operations WHERE id_operation = _idOp;

	RETURN _idOp;

	EXCEPTION
		WHEN check_violation THEN RAISE 'Erreur suppression operation';
END;
$$ LANGUAGE plpgsql;

SELECT reviProjet.supprimer_operation('Damas', 'Christophe', '1234-56789', 'Grolaux', 'Donatien', '5632-12564','04/01/2017', 256);

CREATE OR REPLACE FUNCTION evolution(char(10)) RETURNS SETOF reviProjet.evolution AS $$
DECLARE
	_compte ALIAS FOR $1;
	_operation RECORD;
	_sortie RECORD;
	_solde money := 0;
BEGIN
	IF NOT EXISTS (SELECT c.* FROM reviProjet.comptes c WHERE c.num_compte = _compte) THEN
		RAISE 'compte incorrect';
	END IF;

	FOR _operation IN (SELECT op.date_virement, cd.num_compte as "compte_destination", ud.nom as "nom_destination", ud.prenom as "prenom_destination",cs.num_compte as "compte_source",
	 us.nom as "nom_source", us.prenom as "prenom_source", op.montant
	 FROM reviProjet.operations op, reviProjet.comptes cd, reviProjet.comptes cs, reviProjet.utilisateurs ud, reviProjet.utilisateurs us
	 WHERE (cd.num_compte = _compte OR cs.num_compte = _compte) AND op.compte_source = cs.id_compte AND op.compte_destination = cd.id_compte AND us.id_utilisateur = cs.utilisateur
	 AND ud.id_utilisateur = cd.utilisateur) LOOP
		IF _operation.compte_source = _compte THEN
			_solde = _solde - _operation.montant;
			SELECT _operation.compte_destination, _operation.nom_destination, _operation.prenom_destination, cast(_operation.date_virement as timestamp), _operation.montant, _solde INTO _sortie;
			RETURN NEXT _sortie;
		ELSE
			_solde = _solde + _operation.montant;
			SELECT _operation.compte_source, _operation.nom_source, _operation.prenom_source, cast(_operation.date_virement as timestamp), _operation.montant, _solde INTO _sortie;
			RETURN NEXT _sortie;
		END IF;
	END LOOP;

	EXCEPTION
		WHEN check_violation THEN RAISE 'Erreur evolution';
END;
$$LANGUAGE plpgsql;

SELECT evolution('1234-56789');

CREATE OR REPLACE FUNCTION reviProjet.triggerSoldeCompte() RETURNS TRIGGER AS $$
BEGIN
	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c WHERE c.id_compte = NEW.compte_destination) THEN
		RAISE foreign_key_violation;
	END IF;

	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c WHERE c.id_compte = NEW.compte_source) THEN
		RAISE foreign_key_violation;
	END IF;
	/* Update compte destination */
	UPDATE reviProjet.comptes SET solde = (solde + NEW.montant) WHERE id_compte = NEW.compte_destination;
	/* Update compte source */
	UPDATE reviProjet.comptes SET solde = (solde - NEW.montant) WHERE id_compte = NEW.compte_source;

	RETURN NEW;

	EXCEPTION
		WHEN check_violation THEN RAISE 'Erreur trigger comptes';
END;
$$LANGUAGE plpgsql;

CREATE TRIGGER triggerCompte AFTER INSERT ON reviProjet.operations FOR EACH ROW EXECUTE PROCEDURE reviProjet.triggerSoldeCompte();

CREATE OR REPLACE FUNCTION reviProjet.triggerSoldeUtil() RETURNS TRIGGER AS $$
DECLARE
	_idD integer := 0;
	_idS integer := 0;
BEGIN
	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c, reviProjet.utilisateurs ut WHERE c.utilisateur = ut.id_utilisateur AND c.id_compte = NEW.compte_destination) THEN
		RAISE foreign_key_violation;
	ELSE
		_idD := (SELECT DISTINCT ut.id_utilisateur FROM reviProjet.comptes c, reviProjet.utilisateurs ut WHERE c.utilisateur = ut.id_utilisateur AND c.id_compte = NEW.compte_destination);
	END IF;

	IF NOT EXISTS (SELECT * FROM reviProjet.comptes c, reviPRojet.utilisateurs ut WHERE c.utilisateur = ut.id_utilisateur AND c.id_compte = NEW.compte_source) THEN
		RAISE foreign_key_violation;
	ELSE
		_idS := (SELECT DISTINCT ut.id_utilisateur FROM reviPRojet.comptes c, reviProjet.utilisateurs ut WHERE c.utilisateur = ut.id_utilisateur AND c.id_compte = NEW.compte_source);
	END IF;
	/* Update utilisateur destination */
	UPDATE reviProjet.utilisateurs SET solde = (solde + NEW.montant) WHERE id_utilisateur = _idD;
	/* Update utilisateur source */
	UPDATE reviProjet.utilisateurs SET solde = (solde - NEW.montant) WHERE id_utilisateur = _idS;

	RETURN NEW;

	EXCEPTION
		WHEN check_violation THEN RAISE 'Erreur trigger comptes';
END;
$$LANGUAGE plpgsql;

CREATE TRIGGER triggerUtilisateur AFTER INSERT ON reviProjet.operations FOR EACH ROW EXECUTE PROCEDURE reviProjet.triggerSoldeUtil();

/*SELECT uSource.nom, uSource.prenom, cSource.num_compte, uDest.nom, uDest.prenom, cDest.num_compte, op.date_virement, op.montant
FROM reviProjet.utilisateurs uSource, reviProjet.utilisateurs uDest, reviProjet.comptes cSource, reviProjet.comptes cDest, reviProjet.operations op
WHERE uSource.id_utilisateur = cSource.utilisateur
AND uDest.id_utilisateur =  cDest.utilisateur
AND cSource.id_compte = op.compte_source
AND cDest.id_compte = op.compte_destination
ORDER BY op.date_virement;*/

INSERT INTO reviProjet.operations VALUES(DEFAULT, 1, 2, '01/12/2006', 100);
INSERT INTO reviProjet.operations VALUES(DEFAULT, 2, 5, '02/12/2006', 120);
INSERT INTO reviProjet.operations VALUES(DEFAULT, 3, 4, '03/12/2006', 80);
INSERT INTO reviProjet.operations VALUES(DEFAULT, 4, 3, '04/12/2006', 80);
INSERT INTO reviProjet.operations VALUES(DEFAULT, 5, 4, '05/12/2006', 150);
INSERT INTO reviPRojet.operations VALUES(DEFAULT, 2, 5, '06/12/2006', 120);
INSERT INTO reviProjet.operations VALUES(DEFAULT, 1, 2, '07/12/2006', 100);
INSERT INTO reviProjet.operations VALUES(DEFAULT, 3, 4, '08/12/2006', 80);
INSERT INTO reviProjet.operations VALUES(DEFAULT, 4, 3, '09/12/2006', 80);

SELECT * FROM reviProjet.utilisateurs;

