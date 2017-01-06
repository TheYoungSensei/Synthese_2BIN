/* -------------------------------------------------- PARTIE 1 : SELECT --------------------------------------------------*/

1) 	SELECT au.au_lname, au.au_fname
	FROM authors au
	WHERE lower(au.city) = 'oakland'

4) 	SELECT au.au_lname, au.au_fname
	FROM authors au
	WHERE upper(au.state) = 'CA'
	AND au.phone NOT LIKE '415%'

7)	SELECT DISTINCT t.pub_id
	FROM titles t
	WHERE lower(t.type) = 'psychology'
	AND (t.price > 25 OR t.price < 10)

10)	SELECT DISTINCT t.type
	FROM titles t
	WHERE t.price < 15

--------------------------------------------------- PARTIE 2 : JOINTURE ----------------------------------------------------*/

1) 	SELECT t.title, t.price, p.pub_name
	FROM titles t, publishers p
	WHERE t.pub_id = p.pub_id

4)	SELECT DISTINCT au.state
	FROM authors au, titleauthor ta
	WHERE au.au_id = ta.au_id
	AND au.state is NOT NULL

7)	SELECT DISTINCT t.title
	FROM titles t, titleauthor ta, authors a
	WHERE t.title_id = ta.title_id
	AND a.au_id = ta.au_id
	AND upper(a.state) = 'CA'

10)	SELECT DISTINCT p.pub_name
	FROM publishers p, titles t, salesdetail sd, sales sa
	WHERE t.pub_id = p.pub_id
	AND t.title_id = sd.title_id
	AND sa.ord_num = sd.ord_num
	AND sa.stor_id = sd.stor_id
	AND (sa.date > '1/11/1990' AND sa.date < '1/3/1991')

13)	SELECT DISTINCT a.au_lname, a.au_fname /*ATTENTION EXPERIMENTAL */
	FROM authors a,titleauthor ta1, titleauthor ta2, titles t1, titles t2
	WHERE a.au_id = ta1.au_id
	AND a.au_id = ta2.au_id
	AND ta1.title_id = t1.title_id
	AND ta2.title_id = t2.title_id
	AND t1.title_id < t2.title_id
	AND t1.pub_id < t2.pub_id

16)	SELECT DISTINCT au.state
	FROM authors au, titleauthor ta, titles t, salesdetail sd, stores s, sales sa
	WHERE au.au_id = ta.au_id
	AND ta.title_id = t.title_id
	AND t.title_id = sd.title_id
	AND sd.ord_num = sa.ord_num
	AND sd.stor_id = sa.stor_id
	AND s.stor_id = sa.stor_id
	AND upper(s.state) = 'CA'
	AND date_part('year', sa.date) = '1991'
	AND date_part('month', sa.date) = '2'
	AND au.state is NOT NULL

19)	SELECT t.title, s.stor_name, t.price, sd.qty, sd.qty * t.price, ((sd.qty * t.price) / 100) * 2
	FROM salesdetail sd, stores s, titles t
	WHERE t.title_id = sd.title_id
	AND s.stor_id = sd.stor_id

/*--------------------------------------- PARTIE 3 : FONCTIONS D'AGREGATIONS ------------------------------------------------------*/

1)	SELECT avg(t.price)
	FROM titles t, publishers p
	WHERE t.pub_id = p.pub_id
	AND lower(p.pub_name) = 'algodata infosystems'

4)	SELECT t.title, t.price, count(DISTINCT sd.stor_id)
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title, t.

7)	SELECT t.title_id, t.total_sales, sum(sd.qty)
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title_id, t.total_sales

10)	SELECT sum(sd.qty)
	FROM titles t, publishers p, salesdetail sd, stores s
	WHERE t.pub_id = p.pub_id
	AND upper(p.state) = 'CA'
	AND t.title_id = sd.title_id
	AND sd.stor_id = s.stor_id
	AND upper(s.state) = 'CA'
	AND t.title_id IN (SELECT ta.title_id
						FROM authors a, titleauthor ta
						WHERE a.au_id = ta.au_id
						AND upper(a.state) = 'CA')

/*************************************************** PARTIE 4 : SOUS - SELECT ****************************************************************************************/

1)	SELECT t.title
	FROM titles t, publishers p
	WHERE p.pub_id = t.pub_id
	AND lower(p.pub_name) = 'algodata infosystems'
	AND t.price = (SELECT max(t1.price) FROM titles t1 WHERE t1.pub_id = p.pub_id)

4)	SELECT DISTINCT a.au_lname, a.au_fname
	FROM authors a, titleauthor ta, titles t, publishers p
	WHERE a.au_id = ta.au_id
	AND ta.title_id = t.title_id
	AND t.pub_id = p.pub_id
	AND lower(a.state) = lower(p.state)

7)	SELECT p.pub_name, count(t.title_id)
	FROM publishers p, titles t
	WHERE t.pub_id = p.pub_id
	GROUP BY p.pub_name
	HAVING count(t.title_id) = 0

9)	SELECT DISTINCT t.title
	FROM titles t, salesdetail sd, sales sa
	WHERE t.title_id = sd.title_id
	AND sd.ord_num = sa.ord_num
	AND sd.stor_id = sa.stor_id
	AND sa.date = (SELECT max(sa1.date) FROM sales sa1)

13)	SELECT DISTINCT t.title
	FROM titles t
	WHERE 'CA' = ALL(SELECT upper(a.state) FROM authors a, titleauthor ta WHERE a.au_id = ta.au_id AND ta.title_id = t.title_id)

16)	SELECT t.title
	FROM titles t, titleauthor ta
	WHERE t.title_id IN (SELECT ta1.title_id FROM titleauthor ta1, authors a WHERE ta1.au_id = a.au_id AND upper(a.state) = 'CA')
	AND t.title_id = ta.title_id
	GROUP BY t.title
	HAVING count(ta.au_id) = 1 

/*************************************************** PARTIE 5 : UNION, INTERSECTION, DIFFERENCE ***************************************************************************/

1)	SELECT s.stor_name, sum(sd.qty * t.price)
	FROM stores s LEFT OUTER JOIN salesdetail sd ON s.stor_id = sd.stor_id LEFT OUTER JOIN titles t ON t.title_id = sd.title_id
	GROUP BY s.stor_name
	ORDER BY s.stor_name

4)	SELECT t.title, t.type, a.au_lname, a.au_fname, t.price
	FROM titles t LEFT OUTER JOIN titleauthor ta ON t.title_id = ta.title_id LEFT OUTER JOIN authors a ON a.au_id = ta.au_id
	WHERE t.price > 20
	ORDER BY t.type

7)	SELECT a.au_fname, a.au_lname, a.adress, t.title_id, t.title, 
	FROM authors a LEFT OUTER JOIN titleauthor ta ON ta.au_id = a.au_id LEFT OUTER JOIN titles t ON t.title_id = ta.title_id
	ORDER BY t.title_id 
