DROP SCHEMA IF EXISTS examen CASCADE;

CREATE SCHEMA examen;

CREATE TYPE examen.classementHeure AS (
	nom_zone char(5),
	nbre_aventuriers integer
);

CREATE TYPE examen.historiqueAventurier AS (
	nom_zone char(5),
	date_rep date,
	heure integer
);

CREATE TABLE examen.zones_geographiques (
	id_zone serial PRIMARY KEY,
	nom_zone char(5) CHECK (nom_zone SIMILAR TO '[a-zA-Z]{3}[0-9]{2}') UNIQUE
);

CREATE TABLE examen.aventuriers (
	id_aventurier serial PRIMARY KEY,
	nom_aventurier varchar(255) CHECK (nom_aventurier <> ' ' AND nom_aventurier <> '') UNIQUE,
	nbre_reperages integer NOT NULL CHECK (nbre_reperages >= 0) 
);

CREATE TABLE examen.reperages (
	id_reperage serial PRIMARY KEY,
	zones_geographique integer NOT NULL REFERENCES examen.zones_geographiques(id_zone),
	aventurier integer NOT NULL REFERENCES examen.aventuriers(id_aventurier),
	date_rep date NOT NULL CHECK (date_rep <= now()),
	heure integer NOT NULL CHECK (heure >= 0 AND heure <= 23)
);

CREATE OR REPLACE FUNCTION ajouter_reperage(char(5), varchar, date, integer) RETURNS integer AS $$
DECLARE
	_nomZone ALIAS FOR $1;
	_nomAventurier ALIAS FOR $2;
	_dateRep ALIAS FOR $3;
	_heure ALIAS FOR $4;
	_idZone integer := 0;
	_idAventurier integer := 0;
	_idRep integer := 0;
BEGIN

	IF NOT EXISTS (SELECT * FROM examen.zones_geographiques zg WHERE zg.nom_zone = _nomZone) THEN
		INSERT INTO examen.zones_geographiques VALUES(DEFAULT, _nomZone) RETURNING id_zone INTO _idZone;
	ELSE
		_idZone := (SELECT zg.id_zone FROM examen.zones_geographiques zg WHERE zg.nom_zone = _nomZone);
	END IF;

	IF NOT EXISTS (SELECT * FROM examen.aventuriers av WHERE av.nom_aventurier = _nomAventurier) THEN
		INSERT INTO examen.aventuriers VALUES(DEFAULT, _nomAventurier, 0) RETURNING id_aventurier INTO _idAventurier;
	ELSE
		_idAventurier := (SELECT av.id_aventurier FROM examen.aventuriers av WHERE av.nom_aventurier = _nomAventurier);
	END IF;

	IF EXISTS (SELECT rp.heure FROM examen.reperages rp WHERE rp.date_rep = _dateRep AND rp.heure = _heure 
		AND rp.aventurier = _idAventurier AND rp.zones_geographique = _idZone) THEN
		RAISE 'Attention un reperage a deja ete encode pour cette heure ci';
	END IF;

	INSERT INTO examen.reperages VALUES(DEFAULT, _idZone, _idAventurier, _dateRep, _heure) RETURNING id_reperage INTO _idRep;

	RETURN _idRep;

	/*EXCEPTION
		WHEN check_violation THEN RAISE EXCEPTION  'erreur ajout reperage';*/

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION triggerInsertRep() RETURNS TRIGGER AS $$
BEGIN
	IF NOT EXISTS (SELECT * FROM examen.aventuriers av WHERE av.id_aventurier = NEW.aventurier) THEN
		RAISE foreign_key_violation;
	END IF;

	IF NOT EXISTS (SELECT * FROM examen.zones_geographiques zg WHERE zg.id_zone = NEW.zones_geographique) THEN
		RAISE foreign_key_violation;
	END IF;

	UPDATE examen.aventuriers SET nbre_reperages = (nbre_reperages + 1) WHERE id_aventurier = NEW.aventurier;

	RETURN NEW;

	EXCEPTION
		WHEN check_violation THEN RAISE EXCEPTION 'erreur trigger reperage';
END;
$$LANGUAGE plpgsql;

CREATE TRIGGER triggerRep AFTER INSERT ON examen.reperages FOR EACH ROW EXECUTE PROCEDURE triggerInsertRep();

CREATE OR REPLACE FUNCTION classement_heure(date, integer) RETURNS SETOF examen.classementHeure as $$
DECLARE
	_dateRep ALIAS FOR $1;
	_heure ALIAS FOR $2;
	_sortie examen.classementHeure;
BEGIN
	FOR _sortie IN (SELECT zo.nom_zone, count(DISTINCT av.id_aventurier) as "nombre_aventuriers"
					FROM examen.zones_geographiques zo, examen.aventuriers av, examen.reperages rp 
					WHERE zo.id_zone = rp.id_reperage AND av.id_aventurier = rp.aventurier AND rp.date_rep = _dateRep AND rp.heure = _heure 
					GROUP BY zo.nom_zone ORDER BY "nombre_aventuriers") LOOP
		RETURN NEXT _sortie;
	END LOOP;

	EXCEPTION
		WHEN check_violation THEN RAISE EXCEPTION 'erreur classement heure';
END;
$$LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION historique_aventurier(varchar) RETURNS SETOF examen.historiqueAventurier as $$
DECLARE 
	_nomAventurier ALIAS FOR $1;
	_idAventurier integer := 0;
	_sortie examen.historiqueAventurier;
BEGIN
	
	IF NOT EXISTS (SELECT * FROM examen.aventuriers av WHERE av.nom_aventurier = _nomAventurier) THEN
		RAISE foreign_key_violation; /* ou alors creation d'un nouvel aventurier */
	ELSE
		_idAventurier := (SELECT av.id_aventurier FROM examen.aventuriers av WHERE av.nom_aventurier = _nomAventurier);
	END IF;

	FOR _sortie IN (SELECT zo.nom_zone, rp.date_rep, rp.heure 
					FROM examen.zones_geographiques zo, examen.reperages rp
					WHERE rp.aventurier = _idAventurier AND rp.zones_geographique = zo.id_zone 
					ORDER BY rp.date_rep, rp.heure) LOOP
		RETURN NEXT _sortie;
	END LOOP;

	EXCEPTION 
		WHEN check_violation THEN RAISE EXCEPTION 'erreur historique aventurier';
END;
$$LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION nbre_reperages_aventuriers(varchar) RETURNS integer as $$
DECLARE
	_nomAventurier ALIAS FOR $1;
	_nbreReperages integer := 0;
	_idAventurier integer := 0;
BEGIN
	
	IF NOT EXISTS (SELECT * FROM examen.aventuriers av WHERE av.nom_aventurier = _nomAventurier) THEN
		RAISE foreign_key_violation; /* ou alors creation d'un nouvel aventurier */
	ELSE
		_idAventurier := (SELECT av.id_aventurier FROM examen.aventuriers av WHERE av.nom_aventurier = _nomAventurier);
	END IF;

	_nbreReperages := (SELECT av.nbre_reperages FROM examen.aventuriers av WHERE av.id_aventurier = _idAventurier);

	RETURN _nbreReperages;

	EXCEPTION
		WHEN check_violation THEN RAISE EXCEPTION 'erreur nbre reperages aventuriers';
END;
$$LANGUAGE plpgsql;

INSERT INTO examen.aventuriers VALUES(DEFAULT, 'YOLO', 0);
INSERT INTO examen.zones_geographiques VALUES(DEFAULT, 'AAA01');
SELECT ajouter_reperage('bbb01', 'yolo', cast('25-07-1997' as date), 15);
