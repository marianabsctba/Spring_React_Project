import React, { useState } from 'react';
import axios from 'axios';

const TaskForm = ({ fetchTasks, setError }) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [formError, setFormError] = useState(null);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await axios.post('/api/tasks',
                { title, description },
                { auth: { username: 'admin', password: 'admin' } }
            );
            setTitle('');
            setDescription('');
            fetchTasks(); // Atualize a lista de tarefas após a criação
            setFormError(null); // Limpar qualquer erro anterior
            setError(null); // Limpar qualquer erro anterior
        } catch (error) {
            console.error('Error creating task', error);
            setFormError('Failed to create task.');
            setError('Failed to create task.');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Create Task</h2>
            {formError && <div className="error">{formError}</div>}
            <div>
                <label>Title</label>
                <input
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Description</label>
                <input
                    type="text"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                />
            </div>
            <button type="submit">Create</button>
        </form>
    );
};

export default TaskForm;
