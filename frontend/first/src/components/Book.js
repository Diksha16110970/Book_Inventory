


import React, { useState, useEffect } from 'react';
import './Dashboard.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';

const Book = () => {
  const navigate = useNavigate();
  //const [searchQuery, setSearchQuery] = useState('');
  const [searchQuery, setSearchQuery] = useState(''); 
  const [books, setBooks] = useState([]);
  const [newBook, setNewBook] = useState({
    title: '',
    authorId: '',
    categoryId: '', // Make sure categoryId is part of newBook
    price: '',
    quantity: '',
    isbn: '',
  });
  const [authors, setAuthors] = useState([]);
  const [categories, setCategories] = useState([]); // Store categories here
  const [isEdit, setIsEdit] = useState(false);
  const [editId, setEditId] = useState(null);

  useEffect(() => {
    fetchBooks();
    fetchAuthors();
    fetchCategories(); // Fetch categories on load
  }, []);

  const getAuthHeader = () => {
    const token = localStorage.getItem('token');
    return token ? { Authorization: `Bearer ${token}` } : {};
  };



const getUserId = () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const payload = token.split('.')[1];  // Get the payload (second part)
        const decodedPayload = JSON.parse(atob(payload));  // Decode and parse the payload
        console.log('Decoded Payload:', decodedPayload);  // Log to check the content
        return decodedPayload.sub;  // Use 'sub' field for user_id
      } catch (error) {
        console.error('Error decoding token:', error);
        return null;
      }
    }
    return null;
  };

const fetchBooks = async () => {
    try {
      const userId = getUserId();
      if (!userId) {
        toast.error('User ID is missing');
        return;
      }
  
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:8080/api/auth/getbookbyuserid/${userId}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
  
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Error fetching books: ${errorData.message || response.statusText}`);
      }
  
      const books = await response.json();
      setBooks(books); // Populate books
    } catch (error) {
      console.error('Error fetching books:', error);
      toast.error('Failed to fetch books');
    }
  };
  

  const fetchAuthors = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/auth/getallauthors', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          ...getAuthHeader(),
        },
      });
        console.log(response,"authorresponseresponse");

      if (!response.ok) {
        throw new Error(`Error fetching authors: ${response.statusText}`);
      }

      const authors = await response.json();
      setAuthors(authors);
    } catch (error) {
      console.error('Error fetching authors:', error);
      toast.error('Failed to fetch authors');
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/auth/getallcategories', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          ...getAuthHeader(),
        },
      });
      
      if (!response.ok) {
        throw new Error(`Error fetching categories: ${response.statusText}`);
      }

      const categories = await response.json();
      setCategories(categories); // Populate categories
    } catch (error) {
      console.error('Error fetching categories:', error);
      toast.error('Failed to fetch categories');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewBook({ ...newBook, [name]: value });
  };

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     const url = isEdit
//       ? `http://localhost:8080/api/book/update/${editId}`
//       : 'http://localhost:8080/api/auth/addbook';

//     const method = isEdit ? 'PUT' : 'POST';

//     try {
//         console.log(newBook,"Books")
        
//       const response = await fetch(url, {
//         method,
//         headers: {
//           'Content-Type': 'application/json',
//           ...getAuthHeader(),
//         },
//         body: JSON.stringify(newBook),
//       });

//       if (!response.ok) {
//         const errorData = await response.json();
//         throw new Error(errorData.message || 'Failed to add/update book');
//       }

//       const result = await response.json();
//       toast.success(result.message || (isEdit ? 'Book updated!' : 'Book added!'));
//       fetchBooks();
//       setNewBook({
//         title: '',
//         authorId: '',
//         categoryId: '', // Reset category after submission
//         price: '',
//         quantity: '',
//         isbn: '',
//       });
//       setIsEdit(false);
//     } catch (error) {
//       toast.error(error.message || 'An error occurred');
//     }
//   };

const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    // Determine the URL and HTTP method based on the action (add or edit)
    const url = isEdit
      ? `http://localhost:8080/api/auth/updatebook/${editId}`
      : 'http://localhost:8080/api/auth/addbook';
    const method = isEdit ? 'PUT' : 'POST';
    const payload = token.split('.')[1];  // Get the payload (second part)
    const decodedPayload = JSON.parse(atob(payload));
    try {
      // Format `newBook` payload to match the backend API requirements
      const formattedBook = {
        title: newBook.title,
        userId: decodedPayload.sub, // Replace with dynamic userId if required
        authorId: parseInt(newBook.authorId), // Convert to integer
        genreId: parseInt(newBook.categoryId), // Map `categoryId` to `genreId` and convert to integer
        quantity: parseInt(newBook.quantity), // Convert to integer
        price: parseFloat(newBook.price), // Convert to decimal
        isbn: newBook.isbn,
      };
  
      console.log(formattedBook, 'Formatted Book Payload');
  
      // Make the API call
      const response = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
          ...getAuthHeader(),
        },
        body: JSON.stringify(formattedBook),
      });
  
      // Check for HTTP errors
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to add/update book');
      }
  
      // Parse response and show success message
      const result = await response.json();
      toast.success(result.message || (isEdit ? 'Book updated successfully!' : 'Book added successfully!'));
  
      // Refresh books list and reset the form
      fetchBooks();
      setNewBook({
        title: '',
        authorId: '',
        categoryId: '',
        price: '',
        quantity: '',
        isbn: '',
      });
      setIsEdit(false);
  
    } catch (error) {
      // Display error message using `toast`
      toast.error(error.message || 'An error occurred while processing the request.');
      console.error('Error:', error);
    }
  };
  

  const handleEdit = (book) => {
    setNewBook({
      title: book.title,
      authorId: book.authorId,
      categoryId: book.categoryId, // Set category when editing
      price: book.price,
      quantity: book.quantity,
      isbn: book.isbn,
    });
    setIsEdit(true);
    setEditId(book.bookId);
  };

  const handleDelete = async (id) => {
    const url = `http://localhost:8080/api/auth/delete/${id}`;

    try {
      const response = await fetch(url, {
        method: 'DELETE',
        headers: getAuthHeader(),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to delete book');
      }

      toast.success('Book deleted!');
      setBooks(books.filter((book) => book.bookId !== id));
    } catch (error) {
      toast.error(error.message || 'Failed to delete book');
    }
  };
  
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  // Filter categories based on search query (first character)
  const filteredBooks = books.filter((book) =>
    book.title.toLowerCase().startsWith(searchQuery.toLowerCase())     
  );

  return (
    <div className="dashboard-container">
      <div className="sidebar">
        
        <ul>
          <li onClick={() => navigate('/dashboard')}>Dashboard</li>
          <li onClick={() => navigate('/authors')}>Authors</li>
          <li onClick={() => navigate('/book')}>Book</li>
          <li onClick={() => navigate('/category')}>Category</li>
          <li onClick={() => { localStorage.removeItem('token'); navigate('/'); }}>Logout</li>
        </ul>
      </div>

      <div className="main-content">
        <h2>Books Dashboard</h2>

        <div className="book-form">
          <h3>{isEdit ? 'Update Book' : 'Add Book'}</h3>
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              name="title"
              value={newBook.title}
              onChange={handleChange}
              placeholder="Book Title"
              required
            />
            <select
              name="authorId"
              value={newBook.authorId}
              onChange={handleChange}
              required
            >
              <option value="">Select Author</option>
              {authors.map((author) => (
                <option key={author.authorId} value={author.authorId}>
                  {author.name}
                </option>
              ))}
            </select>
            <select
              name="categoryId"
              value={newBook.categoryId}
              onChange={handleChange}
              required
            >
              <option value="">Select Category</option>
              {categories.map((category) => (
                <option key={category.categoryId} value={category.categoryId}>
                  {category.name}
                </option>
              ))}
            </select>
            <input
              type="number"
              name="price"
              value={newBook.price}
              onChange={handleChange}
              placeholder="Price"
              required
            />
            <input
              type="number"
              name="quantity"
              value={newBook.quantity}
              onChange={handleChange}
              placeholder="Quantity"
              required
            />
            <input
              type="text"
              name="isbn"
              value={newBook.isbn}
              onChange={handleChange}
              placeholder="ISBN"
              required
            />
            <button type="submit">{isEdit ? 'Update' : 'Add'} Book</button>
          </form>
        </div>
        
        <div className="search-bar">
          <input
            type="text"
            placeholder="Search by Title, Author, or Category"
            value={searchQuery}
            onChange={handleSearchChange}
          />
        </div>

        <div className="book-list">
          <h3>Books List</h3>
          <table>
            <thead>
              <tr>
                <th>Title</th>
                <th>Author Name</th>
                <th>Category Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>ISBN</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredBooks.map((book) => (
                <tr key={book.bookId}>
                  <td>{book.title}</td>
                  <td>{book.author.name}</td>
                  <td>{book.genre.name}</td>
                  <td>{book.price}</td>
                  <td>{book.quantity}</td>
                  <td>{book.isbn}</td>
                  <td>
                    <button className="edit-btn" onClick={() => handleEdit(book)}>Edit</button>
                    <button className="delete-btn" onClick={() => handleDelete(book.bookId)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <footer style={{
        padding: '10px',
        backgroundColor: 'rgb(149 153 157)',
        textAlign: 'center',
        fontSize: '14px',
        color: 'white',
        borderTop: '1px solid #dee2e6',
        marginTop: 'auto',
        width: '100%',
        position: 'absolute',
        bottom: '0',
        left: '0',
      }}>
        <p>&copy; {new Date().getFullYear()} Diksha Nagdevate</p>
      </footer>
      </div>

      <ToastContainer />
    </div>
  );
};

export default Book;









