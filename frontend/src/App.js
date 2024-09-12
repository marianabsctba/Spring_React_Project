import React, { useState, useEffect } from 'react';
import TaskList from './components/TaskList';
import TaskForm from './components/TaskForm';
import axios from 'axios';
import './App.css';

function App() {
    const [tasks, setTasks] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchTasks();
    }, []);

    const fetchTasks = async () => {
        try {
            const response = await axios.get('/api/tasks', {
                auth: { username: 'admin', password: 'admin' }
            });
            setTasks(response.data);
            setError(null); // Limpar qualquer erro anterior
        } catch (error) {
            console.error('Error fetching tasks', error);
            setError('Failed to fetch tasks.');
        }
    };

    return (
        <div className="App">
            <h1>Task Manager</h1>
            {error && <div className="error">{error}</div>}
            <TaskForm fetchTasks={fetchTasks} setError={setError} />
            <TaskList tasks={tasks} fetchTasks={fetchTasks} setError={setError} />
        </div>
    );
}

export default App;
