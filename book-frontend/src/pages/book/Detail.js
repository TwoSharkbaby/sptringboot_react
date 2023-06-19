import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';

const Detail = (props) => {
  const id = props.match.params.id;

  const [book, setBook] = useState({
    id: '',
    title: '',
    author: '',
  });

  useEffect(() => {
    fetch('http://localhost:8000/book/' + id)
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  }, []);

  const deleteBook = (id) => {
    fetch('http://localhost:8000/book/' + id, {
      method: 'DELETE',
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'OK') {
          alert('삭제 성공');
          props.history.push('/');
        } else {
          alert('삭제 실패');
        }
      });
  };

  const updateBook = (id) => {
    props.history.push('/updateForm/' + id);
  };

  return (
    <div>
      <h1>책 상세 보기 - 번호 {book.id}</h1>
      <hr />
      <h3>{book.title}</h3>
      <h3>{book.author}</h3>
      <hr />
      <div className="mb-3">
        <Button
          variant="warning"
          onClick={() => updateBook(book.id)}
          className="me-3"
        >
          수정
        </Button>
        <Button variant="danger" onClick={() => deleteBook(book.id)}>
          삭제
        </Button>
      </div>
    </div>
  );
};

export default Detail;
