import React, { useEffect, useState } from 'react';
import BookItem from '../../components/BookItem';

const Home = () => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8000/book') // url, 메서드는 기본값 get이라 생략
      .then((res) => res.json()) // 받은 값을 json으로 변경
      .then((res) => {
        // 변환한 json 값 셋팅
        setBooks(res);
      });
  }, []);

  return (
    <div>
      <h1>책 리스트 보기</h1>
      {books.map((book) => (
        <BookItem key={book.id} book={book} /> // key 값이 있어야 key값을 기준으로 값을 읽어옴
      ))}
    </div>
  );
};

export default Home;
