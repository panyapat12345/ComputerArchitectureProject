def combinations(n, r):
    answer = 0
    stack = [(n, r)]
    x = 0
    while stack:
        n_val, r_val = stack.pop()
        
        if n_val == r_val or r_val == 0:
            answer += 1
        else:
            stack.append((n_val - 1, r_val))
            stack.append((n_val - 1, r_val - 1))
        x = max(x,len(stack))
    print(x)
    return answer

def main():
    n = int(input("Enter n: "))
    r = int(input("Enter r: "))

    result = combinations(n, r)

    print("The result is:", result)

if __name__ == "__main__":
    main()
