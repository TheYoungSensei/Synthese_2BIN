:

  set -x
  cc -o test_01 test_01.c
  cc           -o test_04        test_04.c
  cc -DINIT    -o test_04_init   test_04.c
  cc -DZOMBIE  -o test_04_zombie test_04.c
  cc           -o test_05        test_05.c
  cc           -o test_06        test_06.c
  cc -D_EXIT   -o test_06_exit   test_06.c
  cc           -o test_07        test_07.c
  cc           -o test_08        test_08.c
  cc           -o test_09        test_09.c

exit
