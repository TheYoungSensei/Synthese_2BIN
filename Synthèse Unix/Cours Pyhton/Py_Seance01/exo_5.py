# !/usr/bin/python.

a = 0
b = 1
while (b < 100) :
    print(b)
    b = a + b
    a = b - a
print("finis")