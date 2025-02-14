

// import React, { useState, useEffect } from 'react';
// import './Dashboard.css';
// import { ToastContainer, toast } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css';
// import { useLocation } from 'react-router-dom';
// import { useNavigate } from 'react-router-dom';

// const Category = () => {
//   const navigate = useNavigate();
//   const location = useLocation();
//   const [categories, setCategories] = useState([]);
//   const [newCategory, setNewCategory] = useState({ name: '', description: '' });
//   const [isEdit, setIsEdit] = useState(false);
//   const [editId, setEditId] = useState(null);
//   const [currentPage, setCurrentPage] = useState(1);
//   const [searchQuery, setSearchQuery] = useState(''); // New state for search query
//   const recordsPerPage = 8;

//   useEffect(() => {
//     if (location.state?.message) {
//       toast.success(location.state.message, { position: 'top-right', autoClose: 3000 });
//     }
//     fetchCategories();
//   }, [location.state]);

//   // Decode the JWT token manually
//   const getUserId = () => {
//     const token = localStorage.getItem('token');
//     if (token) {
//       try {
//         const payload = token.split('.')[1];  // Get the payload (second part)
//         const decodedPayload = JSON.parse(atob(payload));  // Decode and parse the payload
//         console.log('Decoded Payload:', decodedPayload); // Log to check the content
//         console.log(decodedPayload.sub);
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

//   // Fetch categories specific to the logged-in user
//   const fetchCategories = async () => {
//     try {
//       const userId = getUserId();
//       if (!userId) {
//         toast.error('User ID is missing');
//         return;
//       }

//       const token = localStorage.getItem('token');
//       const response = await fetch(`http://localhost:8080/api/auth/catuser/${userId}`, {
//         method: 'GET',
//         headers: {
//           'Authorization': `Bearer ${token}`,
//           'Content-Type': 'application/json',
//         },
//       });

//       if (!response.ok) {
//         const errorData = await response.json();
//         throw new Error(`Error fetching categories: ${errorData.message || response.statusText}`);
//       }

//       const categories = await response.json();
//       setCategories(categories);

//     } catch (error) {
//       console.error('Error fetching categories:', error);
//       toast.error('Failed to fetch categories');
//     }
//   };

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setNewCategory({ ...newCategory, [name]: value });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     const userId = getUserId();

//     if (!userId) {
//       toast.error('User ID is missing');
//       return;
//     }

//     const url = isEdit
//       ? `http://localhost:8080/api/auth/updatecategory/${editId}`
//       : 'http://localhost:8080/api/auth/addcategory';

//     const method = isEdit ? 'PUT' : 'POST';
//     const categoryData = {
//       name: newCategory.name,
//       description: newCategory.description,
//       user_id: userId,
//     };

//     try {
//       const response = await fetch(url, {
//         method,
//         headers: {
//           'Content-Type': 'application/json',
//           ...getAuthHeader(),
//         },
//         body: JSON.stringify(categoryData),
//       });

//       if (!response.ok) {
//         const errorData = await response.json();
//         throw new Error(errorData.message || 'Failed to add/update category');
//       }

//       const result = await response.json();
//       toast.success(result.message || (isEdit ? 'Category updated!' : 'Category added!'));
//       fetchCategories();
//       setNewCategory({ name: '', description: '' });
//       setIsEdit(false);
//     } catch (error) {
//       toast.error(error.message || 'An error occurred');
//       console.error('Error in add/update:', error);
//     }
//   };

//   const handleEdit = (category) => {
//     setNewCategory({ name: category.name, description: category.description });
//     setIsEdit(true);
//     setEditId(category.categoryId);
//   };

//   const handleDelete = async (id) => {
//     const url = `http://localhost:8080/api/auth/deletecategory/${id}`;

//     try {
//       const response = await fetch(url, {
//         method: 'DELETE',
//         headers: getAuthHeader(),
//       });

//       if (!response.ok) {
//         const errorData = await response.json();
//         throw new Error(errorData.message || 'Failed to delete category');
//       }

//       toast.success('Category deleted!');
//       setCategories(categories.filter((category) => category.categoryId !== id));
//     } catch (error) {
//       toast.error(error.message || 'Failed to delete category');
//       console.error('Error in deleting category:', error);
//     }
//   };

//   const handleSearchChange = (e) => {
//     setSearchQuery(e.target.value);
//   };

  

//   // Filter categories based on search query (first character)
//   const filteredCategories = categories.filter((category) =>
//     category.name.toLowerCase().startsWith(searchQuery.toLowerCase()) 
//   );

//   const totalPages = Math.ceil(filteredCategories.length / recordsPerPage);
//   const currentCategories = filteredCategories.slice(
//     (currentPage - 1) * recordsPerPage,
//     currentPage * recordsPerPage
//   );

//   const handleNext = () => {
//     if (currentPage < totalPages) setCurrentPage(currentPage + 1);
//   };

//   const handlePrev = () => {
//     if (currentPage > 1) setCurrentPage(currentPage - 1);
//   };

//   // Handle logout
//   const handleLogout = () => {
//     localStorage.removeItem('token');
//     navigate('/');
//   };

//   return (
//     <div className="dashboard-container">
//       <div className="sidebar">
//         <ul>
//           <li onClick={() => navigate('/dashboard')}>Dashboard</li>
//           <li onClick={() => navigate('/authors')}>Authors</li>
//           <li onClick={() => navigate('/book')}>Book</li>
//           <li onClick={() => navigate('/category')}>Category</li>
//           <li onClick={handleLogout}>Logout</li>  {/* Logout button */}
//         </ul>
//       </div>

//       <div className="main-content">
//         <h2>Categories Dashboard</h2>

//         <div>
//           <h3>Add/Update Category</h3>
//           <form onSubmit={handleSubmit}>
//             <input
//               type="text"
//               name="name"
//               value={newCategory.name}
//               onChange={handleChange}
//               placeholder="Category Name"
//               required
//             />
//             <input
//               type="text"
//               name="description"
//               value={newCategory.description}
//               onChange={handleChange}
//               placeholder="Category Description"
//               required
//             />
//             <button type="submit">{isEdit ? 'Update' : 'Add'} Category</button>
//           </form>
//         </div>

//         <div className="author-search">
//           <input
//             type="text"
//             placeholder="Search Categories..."
//             value={searchQuery}
//             onChange={handleSearchChange}
//           />
//         </div>

//         <div>
//           <h3>Categories List</h3>
//           <table border="1">
//             <thead>
//               <tr>
//                 <th>Name</th>
//                 <th>Description</th>
//                 <th>Actions</th>
//               </tr>
//             </thead>
//             <tbody>
//               {currentCategories.map((category) => (
//                 <tr key={category.categoryId}>
//                   <td>{category.name}</td>
//                   <td>{category.description}</td>
//                   <td>
//                     <button className="edit-btn" onClick={() => handleEdit(category)}>Edit</button>
//                     <button className="delete-btn" onClick={() => handleDelete(category.categoryId)}>Delete</button>
//                   </td>
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         </div>

//         <div className="pagination">
//           {/* Previous Button */}
//           <button onClick={handlePrev} disabled={currentPage === 1}>
//             Previous
//           </button>

//           {/* Page Numbers */}
//           {Array.from({ length: totalPages }, (_, index) => (
//             <button
//               key={index + 1}
//               onClick={() => setCurrentPage(index + 1)}
//               className={currentPage === index + 1 ? 'active-page' : ''}
//             >
//               {index + 1}
//             </button>
//           ))}

//           {/* Next Button */}
//           <button onClick={handleNext} disabled={currentPage === totalPages}>
//             Next
//           </button>
//         </div>
//         <footer style={{
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
//       </div>

//       <ToastContainer />
//     </div>
//   );
// };

// export default Category;





import React, { useState, useEffect } from 'react';
import './Dashboard.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const Category = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [categories, setCategories] = useState([]);
  const [newCategory, setNewCategory] = useState({ name: '', description: '' });
  const [isEdit, setIsEdit] = useState(false);
  const [editId, setEditId] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [searchQuery, setSearchQuery] = useState(''); // New state for search query
  const recordsPerPage = 3;
  const [file, setFile] = useState(null);
    const [message, setMessage] = useState('');
    const [uniqueRecords, setUniqueRecords] = useState(0);
    const [duplicateRecords, setDuplicateRecords] = useState(0);

  useEffect(() => {
    if (location.state?.message) {
      toast.success(location.state.message, { position: 'top-right', autoClose: 3000 });
    }
    fetchCategories();
  }, [location.state]);

  // Decode the JWT token manually
  const getUserId = () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const payload = token.split('.')[1];  // Get the payload (second part)
        const decodedPayload = JSON.parse(atob(payload));  // Decode and parse the payload
        console.log('Decoded Payload:', decodedPayload); // Log to check the content
        console.log(decodedPayload.sub);
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

  // Fetch categories specific to the logged-in user
  const fetchCategories = async () => {
    try {
      const userId = getUserId();
      if (!userId) {
        toast.error('User ID is missing');
        return;
      }

      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:8080/api/auth/catuser/${userId}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Error fetching categories: ${errorData.message || response.statusText}`);
      }

      const categories = await response.json();
      setCategories(categories);

    } catch (error) {
      console.error('Error fetching categories:', error);
      toast.error('Failed to fetch categories');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewCategory({ ...newCategory, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userId = getUserId();

    if (!userId) {
      toast.error('User ID is missing');
      return;
    }

    const url = isEdit
      ? `http://localhost:8080/api/auth/updatecategory/${editId}`
      : 'http://localhost:8080/api/auth/addcategory';

    const method = isEdit ? 'PUT' : 'POST';
    const categoryData = {
      name: newCategory.name,
      description: newCategory.description,
      user_id: userId,
    };

    try {
      const response = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
          ...getAuthHeader(),
        },
        body: JSON.stringify(categoryData),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to add/update category');
      }

      const result = await response.json();
      toast.success(result.message || (isEdit ? 'Category updated!' : 'Category added!'));
      fetchCategories();
      setNewCategory({ name: '', description: '' });
      setIsEdit(false);
    } catch (error) {
      toast.error(error.message || 'An error occurred');
      console.error('Error in add/update:', error);
    }
  };

  const handleEdit = (category) => {
    setNewCategory({ name: category.name, description: category.description });
    setIsEdit(true);
    setEditId(category.categoryId);
  };

  const handleDelete = async (id) => {
    const url = `http://localhost:8080/api/auth/deletecategory/${id}`;

    try {
      const response = await fetch(url, {
        method: 'DELETE',
        headers: getAuthHeader(),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to delete category');
      }

      toast.success('Category deleted!');
      setCategories(categories.filter((category) => category.categoryId !== id));
    } catch (error) {
      toast.error(error.message || 'Failed to delete category');
      console.error('Error in deleting category:', error);
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
      const response = await fetch(`http://localhost:8080/api/auth/upload-categories/${userId}`, {
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
  

  // Filter categories based on search query (first character)
  const filteredCategories = categories.filter((category) =>
    category.name.toLowerCase().startsWith(searchQuery.toLowerCase()) 
  );

  const totalPages = Math.ceil(filteredCategories.length / recordsPerPage);
  const currentCategories = filteredCategories.slice(
    (currentPage - 1) * recordsPerPage,
    currentPage * recordsPerPage
  );

  const handleNext = () => {
    if (currentPage < totalPages) setCurrentPage(currentPage + 1);
  };

  const handlePrev = () => {
    if (currentPage > 1) setCurrentPage(currentPage - 1);
  };

  // Handle logout
  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  return (
    <div className="dashboard-container">
      <div className="sidebar">
        <ul>
          <li onClick={() => navigate('/dashboard')}>Dashboard</li>
          <li onClick={() => navigate('/authors')}>Authors</li>
          <li onClick={() => navigate('/book')}>Book</li>
          <li onClick={() => navigate('/category')}>Category</li>
          <li onClick={handleLogout}>Logout</li>  {/* Logout button */}
        </ul>
      </div>

      <div className="main-content">
        <h2>Categories Dashboard</h2>

        <div>
          <h3>Add/Update Category</h3>
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              name="name"
              value={newCategory.name}
              onChange={handleChange}
              placeholder="Category Name"
              required
            />
            <input
              type="text"
              name="description"
              value={newCategory.description}
              onChange={handleChange}
              placeholder="Category Description"
              required
            />
            <button type="submit">{isEdit ? 'Update' : 'Add'} Category</button>
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
            placeholder="Search Categories..."
            value={searchQuery}
            onChange={handleSearchChange}
          />
        </div>

        <div>
          <h3>Categories List</h3>
          <table border="1">
            <thead>
              <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {currentCategories.map((category) => (
                <tr key={category.categoryId}>
                  <td>{category.name}</td>
                  <td>{category.description}</td>
                  <td>
                    <button className="edit-btn" onClick={() => handleEdit(category)}>Edit</button>
                    <button className="delete-btn" onClick={() => handleDelete(category.categoryId)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="pagination">
          {/* Previous Button */}
          <button onClick={handlePrev} disabled={currentPage === 1}>
            Previous
          </button>

          {/* Page Numbers */}
          {Array.from({ length: totalPages }, (_, index) => (
            <button
              key={index + 1}
              onClick={() => setCurrentPage(index + 1)}
              className={currentPage === index + 1 ? 'active-page' : ''}
            >
              {index + 1}
            </button>
          ))}

          {/* Next Button */}
          <button onClick={handleNext} disabled={currentPage === totalPages}>
            Next
          </button>
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
      </div>

      <ToastContainer />
    </div>
  );
};

export default Category;






