import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

const SaveForm = (props) => {
  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  const changeValue = (e) => {
    setBook({
      ...book, // ...book을 안하면 제목을 작성하고 작성자를 작성할때 제목 내용이 사라짐
      [e.target.name]: e.target.value,
    });
  };

  const submitBook = (e) => {
    e.preventDefault();
    fetch('http://localhost:8000/book', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(book),
    })
      .then((res) => {
        if (res.status === 201) {
          return res.json();
        } else {
          return null;
        }
      })
      .then((res) => {
        if (res !== null) {
          props.history.push('/');
        } else {
          alert('등록 실패');
        }
      });
  };

  return (
    <div>
      <h1>책 등록하기</h1>
      <Form onSubmit={submitBook}>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>제목</Form.Label>
          <Form.Control
            type="text"
            placeholder="제목을 입력해주세요"
            onChange={changeValue}
            name="title"
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>작성자</Form.Label>
          <Form.Control
            type="text"
            placeholder="작성자를 입력해주세요"
            onChange={changeValue}
            name="author"
          />
        </Form.Group>
        <Button variant="primary mb-3" type="submit">
          등록하기
        </Button>
      </Form>
    </div>
  );
};

export default SaveForm;
