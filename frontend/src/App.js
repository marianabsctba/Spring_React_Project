import React, { useState, useEffect } from 'react';
import TaskList from './components/TaskList';
import TaskForm from './components/TaskForm';
import axios from 'axios';
import './App.css';

function App() {
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        fetchTasks();
    }, []);

    const fetchTasks = async () => {
        try {
            const response = await axios.get('/api/tasks', {
                auth: { username: 'admin', password: 'admin' }
            });
            setTasks(response.data);
        } catch (error) {
            console.error('Error fetching tasks', error);
        }
    };

    return (
        <div className="App">
            <h1>Task Manager</h1>
            <TaskForm fetchTasks={fetchTasks} />
            <TaskList tasks={tasks} fetchTasks={fetchTasks} />
        </div>
    );
}

export default App;

