import React, { useState } from 'react';
import axios from 'axios';
import EditTaskForm from './EditTaskForm';

const TaskList = ({ tasks, fetchTasks, setError }) => {
    const [editing, setEditing] = useState(false);
    const [currentTask, setCurrentTask] = useState(null);
    const [listError, setListError] = useState(null);

    const deleteTask = async (id) => {
        try {
            await axios.delete(`/api/tasks/${id}`, {
                auth: { username: 'admin', password: 'admin' }
            });
            fetchTasks();
            setListError(null); // Limpar qualquer erro anterior
            setError(null); // Limpar qualquer erro anterior
        } catch (error) {
            console.error('Error deleting task', error);
            setListError('Failed to delete task.');
            setError('Failed to delete task.');
        }
    };

    const editTask = (task) => {
        setCurrentTask(task);
        setEditing(true);
    };

    return (
        <div>
            <h2>Tasks</h2>
            {listError && <div className="error">{listError}</div>}
            {editing ? (
                <EditTaskForm
                    task={currentTask}
                    fetchTasks={fetchTasks}
                    setEditing={setEditing}
                    setError={setError}
                />
            ) : (
                <ul>
                    {tasks.map(task => (
                        <li key={task.id}>
                            <span>{task.title}: {task.description}</span>
                            <button onClick={() => deleteTask(task.id)}>Delete</button>
                            <button onClick={() => editTask(task)}>Edit</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default TaskList;


