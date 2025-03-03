#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_TITLE 100
#define MAX_AUTHOR 100

// Book structure
typedef struct {
    int id;
    char title[MAX_TITLE];
    char author[MAX_AUTHOR];
    int available;
} Book;

// Function prototypes
void addBook();
void displayBooks();
void issueBook();
void returnBook();
void saveBook(Book book);
void loadBooks();

FILE *file;
Book books[100];
int bookCount = 0;

// Main Function
int main() {
    int choice;
    loadBooks();

    while (1) {
        printf("\nLibrary Management System\n");
        printf("1. Add Book\n2. Display Books\n3. Issue Book\n4. Return Book\n5. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);
        getchar(); // Clear input buffer

        switch (choice) {
            case 1: addBook(); break;
            case 2: displayBooks(); break;
            case 3: issueBook(); break;
            case 4: returnBook(); break;
            case 5: exit(0);
            default: printf("Invalid choice! Try again.\n");
        }
    }
    return 0;
}

// Function to add a book
void addBook() {
    Book book;
    printf("Enter Book ID: ");
    scanf("%d", &book.id);
    getchar();
    printf("Enter Book Title: ");
    fgets(book.title, MAX_TITLE, stdin);
    book.title[strcspn(book.title, "\n")] = 0;  // Remove newline character
    printf("Enter Author Name: ");
    fgets(book.author, MAX_AUTHOR, stdin);
    book.author[strcspn(book.author, "\n")] = 0;
    book.available = 1;

    books[bookCount++] = book;
    saveBook(book);
    printf("Book added successfully!\n");
}

// Function to display books
void displayBooks() {
    if (bookCount == 0) {
        printf("No books available.\n");
        return;
    }

    printf("\nAvailable Books:\n");
    printf("%-5s %-30s %-20s %s\n", "ID", "Title", "Author", "Status");
    for (int i = 0; i < bookCount; i++) {
        printf("%-5d %-30s %-20s %s\n", books[i].id, books[i].title, books[i].author, 
               books[i].available ? "Available" : "Issued");
    }
}

// Function to issue a book
void issueBook() {
    int id, found = 0;
    printf("Enter Book ID to issue: ");
    scanf("%d", &id);

    for (int i = 0; i < bookCount; i++) {
        if (books[i].id == id) {
            if (books[i].available) {
                books[i].available = 0;
                printf("Book issued successfully!\n");
            } else {
                printf("Book is already issued.\n");
            }
            found = 1;
            break;
        }
    }
    if (!found) printf("Book not found!\n");
}

// Function to return a book
void returnBook() {
    int id, found = 0;
    printf("Enter Book ID to return: ");
    scanf("%d", &id);

    for (int i = 0; i < bookCount; i++) {
        if (books[i].id == id) {
            if (!books[i].available) {
                books[i].available = 1;
                printf("Book returned successfully!\n");
            } else {
                printf("Book is already available.\n");
            }
            found = 1;
            break;
        }
    }
    if (!found) printf("Book not found!\n");
}

// Function to save book data to file
void saveBook(Book book) {
    file = fopen("library_data.txt", "a");
    if (file == NULL) {
        printf("Error opening file!\n");
        return;
    }
    fprintf(file, "%d,%s,%s,%d\n", book.id, book.title, book.author, book.available);
    fclose(file);
}

// Function to load books from file
void loadBooks() {
    file = fopen("library_data.txt", "r");
    if (file == NULL) return;

    while (fscanf(file, "%d,%99[^,],%99[^,],%d\n", &books[bookCount].id, 
                  books[bookCount].title, books[bookCount].author, &books[bookCount].available) != EOF) {
        bookCount++;
    }
    fclose(file);
}
