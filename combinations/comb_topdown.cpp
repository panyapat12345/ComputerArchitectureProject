#include <stdio.h>

int combination(int n,int r){

}

int main() {
    int n, r;

    // Test case 1: n = 5, r = 2
    n = 5;
    r = 2;
    printf("C(%d, %d) = %d\n", n, r, combination(n, r)); // Expected output: 10

    // Test case 2: n = 6, r = 3
    n = 6;
    r = 3;
    printf("C(%d, %d) = %d\n", n, r, combination(n, r)); // Expected output: 20

    // Test case 3: n = 10, r = 5
    n = 10;
    r = 5;
    printf("C(%d, %d) = %d\n", n, r, combination(n, r)); // Expected output: 252

    return 0;
}