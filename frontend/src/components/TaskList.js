import React, { useState } from 'react';
import axios from 'axios';
import EditTaskForm from './EditTaskForm';

const TaskList = ({ tasks, fetchTasks }) => {
    const [editing, setEditing] = useState(false);
    const [currentTask, setCurrentTask] = useState(null);

    const deleteTask = async (id) => {
        try {
            await axios.delete(`/api/tasks/${id}`, {
                auth: { username: 'admin', password: 'admin' }
            });
            fetchTasks();
        } catch (error) {
            console.error('Error deleting task', error);
        }
    };

    const editTask = (task) => {
        setCurrentTask(task);
        setEditing(true);
    };

    return (
        <div>
            <h2>Tasks</h2>
            {editing ? (
                <EditTaskForm
                    task={currentTask}
                    fetchTasks={fetchTasks}
                    setEditing={setEditing}
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

