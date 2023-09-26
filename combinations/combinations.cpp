#include <stdio.h>

int combination(int n, int r) {
    int arr[n + 1][r + 1];

    // Initialize the base cases
    for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= r; j++) {
            if (j == 0 || i == j) {
                arr[i][j] = 1;
            } else {
                arr[i][j] = 0;
            }
        }
    }

    // Fill in the arr table using bottom-up approach
    for (int i = 2; i <= n; i++) {
        for (int j = 1; j < i && j <= r; j++) {
            arr[i][j] = arr[i - 1][j] + arr[i - 1][j - 1];
        }
    }

    // The final result is stored in arr[n][r]
    return arr[n][r];
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