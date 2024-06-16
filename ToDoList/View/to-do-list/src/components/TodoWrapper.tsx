import { useState } from 'react';
import Board from './Board.tsx';
import NewTaskModal from './NewTaskModal.tsx';

const TodoWrapper = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    return (
        <div className='App'>
            <div style={styles.header}>
                <h1 style={styles.h1}>Lista de Tarefas</h1>
                <button onClick={openModal} style={styles.addButton}>+ Adicionar Tarefa</button>
            </div>
            <Board />
            {isModalOpen && <NewTaskModal onClose={closeModal} />} {/* Renderize o modal apenas se isModalOpen for verdadeiro */}
        </div>
    );
};

const styles = {
    h1: {
        textAlign: 'center',
        marginTop: '20px',
        color: '#333',
    },
    header: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: '20px',
    },
    addButton: {
        padding: '10px 20px',
        backgroundColor: '#007bff',
        color: '#fff',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        fontSize: '16px',
    },
};
export default TodoWrapper;
