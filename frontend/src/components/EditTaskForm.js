import React, { useState } from 'react';
import axios from 'axios';

const EditTaskForm = ({ task, fetchTasks, setEditing }) => {
    const [title, setTitle] = useState(task.title);
    const [description, setDescription] = useState(task.description);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await axios.put(`/api/tasks/${task.id}`, { title, description });
            fetchTasks();
            setEditing(false);
        } catch (error) {
            console.error('Error updating task', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Edit Task</h2>
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
            <button type="submit">Update</button>
            <button type="button" onClick={() => setEditing(false)}>Cancel</button>
        </form>
    );
};

export default EditTaskForm;
