//Register allocations
x0 = zero
x1 = return address
x2 = stack pointer
x3 = temporary value
x4 = local variable
x5 = input (n)
x6 = input (r)
x7 = return value of the function (ans)

        lw 0 5 n        // x5 = n
        lw 0 6 r        // x6 = r
        lw 0 1 neg1     // x1 = -1
        lw 0 3 plus1    // x3 = +1
        sw 2 5 addr     // push n to stack
        add 2 3 2       // +1 to stack pointer
        sw 2 6 addr     // push r to stack
comb    beq 1 2 done    // if stack pointer = 0, done
        lw 2 6 addr     // top r value from stack
        sw 2 0 addr     // pop stack
        add 2 1 2       // -1 from stack pointer
        lw 2 5 addr     // top n value from stack
        sw 2 0 addr     // pop stack
        beq 1 2 base    // if n = r go to base
        beq 0 6 base    // if r = 0 go to base
        add 5 1 5       // n -= 1 (combination(n-1,r) here)
        sw 2 5 addr     // push n to stack
        add 2 3 2       // +1 to stack pointer
        sw 2 6 addr     // push r to stack
        add 6 1 6       // r -= 1 (combination(n-1,r-1) here)
        add 2 3 2       // +1 to stack pointer
        sw 2 5 addr     // push n to stack
        add 2 3 2       // +1 to stack pointer
        sw 2 6 addr     // push r to stack
        beq 0 0 comb    // back to comb
base    add 7 3 7       // ans += 1
        add 2 1 2       // -1 from stack pointer
        beq 0 0 comb    // back to comb
        noop
done    halt            // end of program
neg1    .fill -1
plus1   .fill 1
addr    .fill 0
n       .fill input1
r       .fill input2



