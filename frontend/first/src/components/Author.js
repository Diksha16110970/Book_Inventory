


// import React, { useState, useEffect } from 'react';
// import './Dashboard.css';
// import { ToastContainer, toast } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css';
// import { useNavigate } from 'react-router-dom';

// const Author = () => {
//   const navigate = useNavigate();
//   const [authors, setAuthors] = useState([]);
//   const [newAuthor, setNewAuthor] = useState({ name: '' });
//   const [isEdit, setIsEdit] = useState(false);
//   const [editId, setEditId] = useState(null);
//   const [searchQuery, setSearchQuery] = useState('');
//   const [currentPage, setCurrentPage] = useState(1);
//   const recordsPerPage = 9;

//   useEffect(() => {
//     fetchAuthors();
//   }, []);

//   const getUserId = () => {
//     const token = localStorage.getItem('token');
//     if (token) {
//       try {
//         const payload = token.split('.')[1];  
//         const decodedPayload = JSON.parse(atob(payload));  
//         return decodedPayload.sub;  
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
//       const totalPages = Math.ceil(authors.length / recordsPerPage);
//       setCurrentPage(totalPages); // Move to last page after fetching
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

//     // Trim spaces from the name
//     const trimmedName = newAuthor.name.trim();

//     // Check if the author already exists
//     const authorExists = authors.some((author) => author.name.trim().toLowerCase() === trimmedName.toLowerCase());
//     if (authorExists) {
//       toast.error('Author name already exists!');
//       return;
//     }

//     const url = isEdit
//       ? `http://localhost:8080/api/auth/updateauthor/${editId}`
//       : 'http://localhost:8080/api/auth/addauthor';

//     const method = isEdit ? 'PUT' : 'POST';
//     const authorData = {
//       name: trimmedName,  // Use trimmed name
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

//       toast.success(isEdit ? 'Author updated!' : 'Author added!');
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
//       const updatedAuthors = authors.filter((author) => author.authorId !== id);
//       setAuthors(updatedAuthors);
//       const totalPages = Math.ceil(updatedAuthors.length / recordsPerPage);
//       setCurrentPage((prev) => Math.min(prev, totalPages)); 
//     } catch (error) {
//       toast.error(error.message || 'Failed to delete author');
//     }
//   };

//   const handleSearchChange = (e) => {
//     setSearchQuery(e.target.value);
//   };

//   const filteredAuthors = authors.filter((author) =>
//     author.name.toLowerCase().startsWith(searchQuery.toLowerCase())
//   );

//   const totalPages = Math.ceil(filteredAuthors.length / recordsPerPage);
//   const indexOfLastRecord = currentPage * recordsPerPage;
//   const indexOfFirstRecord = indexOfLastRecord - recordsPerPage;
//   const currentRecords = filteredAuthors.slice(indexOfFirstRecord, indexOfLastRecord);

//   return (
//     <div className="dashboard-container">
//       <div className="sidebar">
//         <ul>
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
//               {currentRecords.map((author) => (
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

//         <div className="pagination">
//           <button disabled={currentPage === 1} onClick={() => setCurrentPage(currentPage - 1)}>Previous</button>
//           <span> Page {currentPage} of {totalPages} </span>
//           <button disabled={currentPage === totalPages} onClick={() => setCurrentPage(currentPage + 1)}>Next</button>
//         </div>
//       </div>

//       <footer style={{
//         padding: '10px',
//         backgroundColor: 'rgb(149 153 157)',
//         textAlign: 'center',
//         fontSize: '14px',
//         color: 'white',
//         borderTop: '1px solid #dee2e6',
//         marginTop: 'auto',
//         width: '100%',
//         position: 'absolute',
//         bottom: '0',
//         left: '0',
//       }}>
//         <p>&copy; {new Date().getFullYear()} Diksha Nagdevate Tech Titan's Inventory. All Rights Reserved.</p>
//       </footer>

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
  const [newAuthor, setNewAuthor] = useState({ name: '' });
  const [isEdit, setIsEdit] = useState(false);
  const [editId, setEditId] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const recordsPerPage = 4;
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState('');
  const [uniqueRecords, setUniqueRecords] = useState(0);
  const [duplicateRecords, setDuplicateRecords] = useState(0);

  useEffect(() => {
    fetchAuthors();
  }, []);

  const getUserId = () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const payload = token.split('.')[1];
        const decodedPayload = JSON.parse(atob(payload));
        return decodedPayload.sub;
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
      const totalPages = Math.ceil(authors.length / recordsPerPage);
      setCurrentPage(totalPages); // Move to last page after fetching
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

    // Trim spaces from the name
    const trimmedName = newAuthor.name.trim();

    // Check if the author already exists
    const authorExists = authors.some((author) => author.name.trim().toLowerCase() === trimmedName.toLowerCase());
    if (authorExists) {
      toast.error('Author name already exists!');
      return;
    }

    const url = isEdit
      ? `http://localhost:8080/api/auth/updateauthor/${editId}`
      : 'http://localhost:8080/api/auth/addauthor';

    const method = isEdit ? 'PUT' : 'POST';
    const authorData = {
      name: trimmedName,  // Use trimmed name
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

      toast.success(isEdit ? 'Author updated!' : 'Author added!');
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
      const updatedAuthors = authors.filter((author) => author.authorId !== id);
      setAuthors(updatedAuthors);
      const totalPages = Math.ceil(updatedAuthors.length / recordsPerPage);
      setCurrentPage((prev) => Math.min(prev, totalPages));
    } catch (error) {
      toast.error(error.message || 'Failed to delete author');
    }
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleCSVSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    const userId = getUserId();

    if (!file) {
      setMessage('Please select a CSV file.');
      return;
    }

    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await fetch(`http://localhost:8080/api/auth/upload-authors/${userId}`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
        },
        body: formData,
      });

      const data = await response.json();

      if (response.ok) {
        setMessage(data.message);
        setUniqueRecords(data.uniqueRecordsAdded);
        setDuplicateRecords(data.duplicateRecordsSkipped);
      } else {
        setMessage(data.message);
      }
    } catch (error) {
      setMessage('Error uploading CSV file.');
    }
  };

  const filteredAuthors = authors.filter((author) =>
    author.name.toLowerCase().startsWith(searchQuery.toLowerCase())
  );

  const totalPages = Math.ceil(filteredAuthors.length / recordsPerPage);
  const indexOfLastRecord = currentPage * recordsPerPage;
  const indexOfFirstRecord = indexOfLastRecord - recordsPerPage;
  const currentRecords = filteredAuthors.slice(indexOfFirstRecord, indexOfLastRecord);

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

        {/* CSV Upload Form */}
        <div className="csv-upload-form">
          <h3>Upload Authors from CSV</h3>
          <form onSubmit={handleCSVSubmit}>
            <input type="file" accept=".csv" onChange={handleFileChange} />
            <button type="submit">Upload CSV</button>
          </form>
          {message && <p>{message}</p>}
          {uniqueRecords > 0 && <p>Unique records added: {uniqueRecords}</p>}
          {duplicateRecords > 0 && <p>Duplicate records skipped: {duplicateRecords}</p>}
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
              {currentRecords.map((author) => (
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

        <div className="pagination">
          <button disabled={currentPage === 1} onClick={() => setCurrentPage(currentPage - 1)}>Previous</button>
          <span> Page {currentPage} of {totalPages} </span>
          <button disabled={currentPage === totalPages} onClick={() => setCurrentPage(currentPage + 1)}>Next</button>
        </div>
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
        <p>&copy; {new Date().getFullYear()} Diksha Nagdevate Tech Titan's Inventory. All Rights Reserved.</p>
      </footer>

      <ToastContainer />
    </div>
  );
};

export default Author;

