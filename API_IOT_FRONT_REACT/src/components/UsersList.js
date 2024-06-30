// src/components/UsersList.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import './UsersList.css';

function UsersList() {
    const [pessoas, setPessoas] = useState([]);
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [editUser, setEditUser] = useState({ id: '', nome: '', email: '' });

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = () => {
        axios.get('http://localhost/apispring/pessoa')
            .then(response => setPessoas(response.data))
            .catch(error => console.error('Erro ao buscar pessoas:', error));
    };

    const handleDelete = (id) => {
        axios.delete(`http://localhost/apispring/pessoa/${id}`)
            .then(() => {
                fetchUsers(); // Atualiza a lista após a deleção
            })
            .catch(error => console.error('Erro ao deletar pessoa:', error));
    };

    const openModal = (pessoa) => {
        setEditUser(pessoa);
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };

    const handleUpdate = () => {
        axios.put(`http://localhost/apispring/pessoa/${editUser.id}`, editUser)
            .then(() => {
                fetchUsers(); // Atualiza a lista após o update
                closeModal(); // Fecha o modal após o update
            })
            .catch(error => console.error('Erro ao atualizar pessoa:', error));
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setEditUser({ ...editUser, [name]: value });
    };

    return (
        <div className="users-list-container">
            <h2>Lista de Usuários</h2>
            <table>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {pessoas.map(pessoa => (
                        <tr key={pessoa.id}>
                            <td className="user-name">{pessoa.nome}</td>
                            <td className="user-email">{pessoa.email}</td>
                            <td>
                                <button onClick={() => openModal(pessoa)}>Editar</button>
                                <button onClick={() => handleDelete(pessoa.id)}>Deletar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* Modal flutuante */}
            <Modal
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                className="modal"
                overlayClassName="overlay"
                contentLabel="Editar Usuário"
            >
                <h2>Editar Usuário</h2>
                <form>
                    <label>Nome:</label>
                    <input type="text" name="nome" value={editUser.nome} onChange={handleInputChange} />
                    <label>Email:</label>
                    <input type="email" name="email" value={editUser.email} onChange={handleInputChange} />
                    <button type="button" onClick={handleUpdate}>Atualizar</button>
                </form>
            </Modal>
        </div>
    );
}

export default UsersList;
