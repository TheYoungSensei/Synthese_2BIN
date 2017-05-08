:

  set -x

  cc -o solex       solex.c
  cc -o test_dup1   test_dup1.c
  cc -o test_dup2   test_dup2.c
  cc -o test_mxnf   test_mxnf.c
  cc -o test_lock1  test_lock1.c
  cc -o test_lock2  test_lock2.c

exit
