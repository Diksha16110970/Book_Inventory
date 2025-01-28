// import React, { useState, useEffect } from 'react';
// import './Dashboard.css';
// import { ToastContainer, toast } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css';
// import { useNavigate } from 'react-router-dom';

// const Author = () => {
//   const navigate = useNavigate();
//   const [authors, setAuthors] = useState([]);
//   const [newAuthor, setNewAuthor] = useState({ name: '', description: '' });
//   const [isEdit, setIsEdit] = useState(false);
//   const [editId, setEditId] = useState(null);
//   const [searchQuery, setSearchQuery] = useState(''); 
//   const [currentPage, setCurrentPage] = useState(1);
//   const recordsPerPage = 10;

//   useEffect(() => {
//     fetchAuthors();
//   }, []);

//   const getUserId = () => {
//     const token = localStorage.getItem('token');
//     if (token) {
//       try {
//         const payload = token.split('.')[1];  // Get the payload (second part)
//         const decodedPayload = JSON.parse(atob(payload));  // Decode and parse the payload
//         return decodedPayload.sub;  // Use 'sub' field for user_id
//       } catch (error) {
//         console.error('Error decoding token:', error);
//         return null;
//       }
//     }
//     return null;
//   };

//   const getAuthHeader = () => {
//     const token = localStorage.getItem('token');
//     return token ? { Authorization: `Bearer ${token}` } : {};
//   };

//   const fetchAuthors = async () => {
//     try {
//       const userId = getUserId();
//       if (!userId) {
//         toast.error('User ID is missing');
//         return;
//       }

//       const response = await fetch(`http://localhost:8080/api/auth/authoruser/${userId}`, {
//         method: 'GET',
//         headers: {
//           'Content-Type': 'application/json',
//           ...getAuthHeader(),
//         },
//       });

//       if (!response.ok) {
//         throw new Error(`Error fetching authors: ${response.statusText}`);
//       }

//       const authors = await response.json();
//       setAuthors(authors);
//     } catch (error) {
//       console.error('Error fetching authors:', error);
//       toast.error('Failed to fetch authors');
//     }
//   };

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setNewAuthor({ ...newAuthor, [name]: value });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     const userId = getUserId();

//     if (!userId) {
//       toast.error('User ID is missing');
//       return;
//     }

//     const url = isEdit
//       ? `http://localhost:8080/api/auth/updateauthor/${editId}`
//       : 'http://localhost:8080/api/auth/addauthor';

//     const method = isEdit ? 'PUT' : 'POST';
//     const authorData = {
//       name: newAuthor.name,
//       user_id: userId,
//     };

//     try {
//       const response = await fetch(url, {
//         method,
//         headers: {
//           'Content-Type': 'application/json',
//           ...getAuthHeader(),
//         },
//         body: JSON.stringify(authorData),
//       });

//       if (!response.ok) {
//         const errorData = await response.json();
//         throw new Error(errorData.message || 'Failed to add/update author');
//       }

//       const result = await response.json();
//       toast.success(result.message || (isEdit ? 'Author updated!' : 'Author added!'));
//       fetchAuthors();
//       setNewAuthor({ name: '' });
//       setIsEdit(false);
//     } catch (error) {
//       toast.error(error.message || 'An error occurred');
//     }
//   };

//   const handleEdit = (author) => {
//     setNewAuthor({ name: author.name });
//     setIsEdit(true);
//     setEditId(author.authorId);
//   };

//   const handleDelete = async (id) => {
//     const url = `http://localhost:8080/api/auth/deleteauthor/${id}`;

//     try {
//       const response = await fetch(url, {
//         method: 'DELETE',
//         headers: getAuthHeader(),
//       });

//       if (!response.ok) {
//         const errorData = await response.json();
//         throw new Error(errorData.message || 'Failed to delete author');
//       }

//       toast.success('Author deleted!');
//       setAuthors(authors.filter((author) => author.authorId !== id));
//     } catch (error) {
//       toast.error(error.message || 'Failed to delete author');
//     }
//   };
//   const handleSearchChange = (e) => {
//     setSearchQuery(e.target.value);
//   };

//   // Filter authors based on search query
//   const filteredAuthors = authors.filter((author) =>
//     author.name.toLowerCase().includes(searchQuery.toLowerCase())
//   );

//   return (
//     <div className="dashboard-container">
//       <div className="sidebar">
//       <ul>
//           <li onClick={() => navigate('/dashboard')}>Dashboard</li>
//           <li onClick={() => navigate('/authors')}>Authors</li>
//           <li onClick={() => navigate('/book')}>Book</li>
//           <li onClick={() => navigate('/category')}>Category</li>
//           <li onClick={() => { localStorage.removeItem('token'); navigate('/'); }}>Logout</li>
//         </ul>
//       </div>

//       <div className="main-content">
//         <h2>Authors Dashboard</h2>

//         <div className="author-form">
//           <h3>Add/Update Author</h3>
//           <form onSubmit={handleSubmit}>
//             <input
//               type="text"
//               name="name"
//               value={newAuthor.name}
//               onChange={handleChange}
//               placeholder="Author Name"
//               required
//             />
//             <button type="submit">{isEdit ? 'Update' : 'Add'} Author</button>
//           </form>
//         </div>
//         <div className="author-search">
//           <input
//             type="text"
//             placeholder="Search Authors..."
//             value={searchQuery}
//             onChange={handleSearchChange}
//           />
//         </div>
//         <div className="author-list">
//           <h3>Authors List</h3>
//           <table>
//             <thead>
//               <tr>
//                 <th>Name</th>
//                 <th>Actions</th>
//               </tr>
//             </thead>
//             <tbody>
//               {authors.map((author) => (
//                 <tr key={author.authorId}>
//                   <td>{author.name}</td>
//                   <td>
//                     <button className="edit-btn" onClick={() => handleEdit(author)}>Edit</button>
//                     <button className="delete-btn" onClick={() => handleDelete(author.authorId)}>Delete</button>
//                   </td>
            
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         </div>
//       </div>

//       <ToastContainer />
//     </div>
//   );
// };

// export default Author;

import React, { useState, useEffect } from 'react';
import './Dashboard.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';

const Author = () => {
  const navigate = useNavigate();
  const [authors, setAuthors] = useState([]);
  const [newAuthor, setNewAuthor] = useState({ name: '', description: '' });
  const [isEdit, setIsEdit] = useState(false);
  const [editId, setEditId] = useState(null);
  const [searchQuery, setSearchQuery] = useState(''); 
  const [currentPage, setCurrentPage] = useState(1);
  const recordsPerPage = 10;

  useEffect(() => {
    fetchAuthors();
  }, []);

  const getUserId = () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const payload = token.split('.')[1];  // Get the payload (second part)
        const decodedPayload = JSON.parse(atob(payload));  // Decode and parse the payload
        return decodedPayload.sub;  // Use 'sub' field for user_id
      } catch (error) {
        console.error('Error decoding token:', error);
        return null;
      }
    }
    return null;
  };

  const getAuthHeader = () => {
    const token = localStorage.getItem('token');
    return token ? { Authorization: `Bearer ${token}` } : {};
  };

  const fetchAuthors = async () => {
    try {
      const userId = getUserId();
      if (!userId) {
        toast.error('User ID is missing');
        return;
      }

      const response = await fetch(`http://localhost:8080/api/auth/authoruser/${userId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          ...getAuthHeader(),
        },
      });

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

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewAuthor({ ...newAuthor, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userId = getUserId();

    if (!userId) {
      toast.error('User ID is missing');
      return;
    }

    const url = isEdit
      ? `http://localhost:8080/api/auth/updateauthor/${editId}`
      : 'http://localhost:8080/api/auth/addauthor';

    const method = isEdit ? 'PUT' : 'POST';
    const authorData = {
      name: newAuthor.name,
      user_id: userId,
    };

    try {
      const response = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
          ...getAuthHeader(),
        },
        body: JSON.stringify(authorData),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to add/update author');
      }

      const result = await response.json();
      toast.success(result.message || (isEdit ? 'Author updated!' : 'Author added!'));
      fetchAuthors();
      setNewAuthor({ name: '' });
      setIsEdit(false);
    } catch (error) {
      toast.error(error.message || 'An error occurred');
    }
  };

  const handleEdit = (author) => {
    setNewAuthor({ name: author.name });
    setIsEdit(true);
    setEditId(author.authorId);
  };

  const handleDelete = async (id) => {
    const url = `http://localhost:8080/api/auth/deleteauthor/${id}`;

    try {
      const response = await fetch(url, {
        method: 'DELETE',
        headers: getAuthHeader(),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to delete author');
      }

      toast.success('Author deleted!');
      setAuthors(authors.filter((author) => author.authorId !== id));
    } catch (error) {
      toast.error(error.message || 'Failed to delete author');
    }
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  // Filter authors based on search query
  const filteredAuthors = authors.filter((author) =>
    //author.name.toLowerCase().includes(searchQuery.toLowerCase())
  author.name.toLowerCase().startsWith(searchQuery.toLowerCase())
 
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
        <h2>Authors Dashboard</h2>

        <div className="author-form">
          <h3>Add/Update Author</h3>
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              name="name"
              value={newAuthor.name}
              onChange={handleChange}
              placeholder="Author Name"
              required
            />
            <button type="submit">{isEdit ? 'Update' : 'Add'} Author</button>
          </form>
        </div>

        <div className="author-search">
          <input
            type="text"
            placeholder="Search Authors..."
            value={searchQuery}
            onChange={handleSearchChange}
          />
        </div>

        <div className="author-list">
          <h3>Authors List</h3>
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredAuthors.map((author) => (
                <tr key={author.authorId}>
                  <td>{author.name}</td>
                  <td>
                    <button className="edit-btn" onClick={() => handleEdit(author)}>Edit</button>
                    <button className="delete-btn" onClick={() => handleDelete(author.authorId)}>Delete</button>
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

export default Author;

