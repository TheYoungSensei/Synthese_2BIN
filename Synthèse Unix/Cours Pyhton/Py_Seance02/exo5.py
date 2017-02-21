def palindrome(li):
    for i in range(0,len(li)//2):
        if li[i] != li[(len(li) - 1) - i]:
            print("Je ne suis pas un palindrome")
            return
    print("Je suis un palindrome")
    return

#script principal

palindrome("engagelejeuquejelegagne")