package com.malivasileva.librarian.presentation.viewModels;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.malivasileva.librarian.domain.usecases.GetLendingsForBookWithIdUseCase;
import com.malivasileva.model.Book;
import com.malivasileva.model.Lending;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class BookLendingsViewModel extends ViewModel {

    private final GetLendingsForBookWithIdUseCase getLendingsForBookWithIdUseCase;

    private MutableLiveData<List<Lending>> lendingsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> eventLiveData = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public BookLendingsViewModel(
            GetLendingsForBookWithIdUseCase getLendingsForBookWithIdUseCase
    ) {
        this.getLendingsForBookWithIdUseCase = getLendingsForBookWithIdUseCase;
    }

    public LiveData<List<Lending>> getLendingLiveData() { return lendingsLiveData; }
    public LiveData<String> getEventLiveData() { return eventLiveData; }

    public void getBook(int num) {
        disposable.add(
                getLendingsForBookWithIdUseCase.execute(num)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( lendings -> {
                            lendingsLiveData.setValue(lendings);
                                },
                                throwable -> {
                                    eventLiveData.setValue(throwable.getMessage());
                                }
                        )
        );
    }

    public void exportToPdf(Context context, int bookId){
        List<Lending> lendings = lendingsLiveData.getValue();
        String bookTitle = lendings.get(0).getTitle();
        String author = lendings.get(0).getAuthors();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "lendingsFor" + bookId + ".pdf");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), contentValues);

        if (uri != null) {
            try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
                PdfDocument pdfDocument = new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                Paint paint = new Paint();

                int x = 30; // Отступ слева
                int y = 50; // Начальная позиция
                int lineHeight = 20; // Высота строки

//                // 1. Жирная надпись "Форма 4", выравнивание справа
//                paint.setTextSize(16);
//                paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
//                String formText = "Отчет о выдачах книги";
//                float formTextWidth = paint.measureText(formText);
//                canvas.drawText(formText, pageInfo.getPageWidth() - formTextWidth - 50, y, paint);
//                y += 30;

                // 2. По центру жирный заголовок
                paint.setTextSize(14);
                String title = "Выдачи книги №" + bookId +
                        "\"" + bookTitle + "\" " + author;
                paint.setTextAlign(Paint.Align.CENTER);
                drawMultilineText(canvas, paint, title, pageInfo.getPageWidth() / 2, y, 500);
                y += 70;

//                // 3. Подчёркнутый текст
//                paint.setTextSize(12);
//                paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
//                paint.setUnderlineText(true);
//                String underlinedText = "Иркутский национальный исследовательский технический университет \n" + code + " " + specialty + " \n" + series;
//                drawMultilineText(canvas, paint, underlinedText, pageInfo.getPageWidth() / 2, y, 500);
//                paint.setUnderlineText(false);
//                y += 50;


                // Ширина столбцов
                int[] columnWidths = {30, 80, 120, 120, 70, 70};

                // Заголовки
                String[] headers = {
                        "№", "ID читателя", "ФИО читателя", "Дата выдачи",
                        "Требуемая дата", "Дата возврата"
                };

                // Уменьшаем размер шрифта для компактности
                paint.setTextSize(10);

                // Рассчитываем высоту строки для заголовков
                int headerHeight = calculateRowHeight(paint, headers, columnWidths);
                drawTableRow(canvas, paint, x, y, columnWidths, headerHeight, headers, true);
                y += headerHeight;

                // Данные книг
                int rowIndex = 1;
                for (Lending lending : lendings) {
                    String returnDate;
                    if (lending.getReturnDate() != null) {
                        returnDate = lending.getReturnDate().toString();
                    } else {
                        returnDate = "";
                    }
                    String[] row = {
                            String.valueOf(lending.getId()),
                            String.valueOf(lending.getReaderId()),
                            lending.getReaderName(),
                            lending.getLendDate().toString(),
                            lending.getRequiredDate().toString(),
                            returnDate
                    };

                    // Рассчитываем высоту строки для текущей книги
                    int rowHeight = calculateRowHeight(paint, row, columnWidths);

                    // Проверяем, поместится ли строка на текущей странице
                    if (y + rowHeight > pageInfo.getPageHeight() - 50) {
                        pdfDocument.finishPage(page);
                        pageInfo = new PdfDocument.PageInfo.Builder(595, 842, pdfDocument.getPages().size() + 1).create();
                        page = pdfDocument.startPage(pageInfo);
                        canvas = page.getCanvas();
                        y = 50; // Сброс позиции
                    }

                    // Рисуем строку
                    drawTableRow(canvas, paint, x, y, columnWidths, rowHeight, row, false);
                    y += rowHeight;
                }

                pdfDocument.finishPage(page);
                pdfDocument.writeTo(outputStream);
                pdfDocument.close();

                eventLiveData.setValue("PDF сохранён в папке Downloads");
            } catch (IOException e) {
                e.printStackTrace();
                eventLiveData.setValue("Ошибка при создании PDF");
            }
        }
    }

    private void drawTableRow(Canvas canvas, Paint paint, int x, int y, int[] columnWidths, int rowHeight, String[] cellTexts, boolean isHeader) {
        Paint linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);

        // Обводка строки
        int rowWidth = 0;
        for (int width : columnWidths) {
            rowWidth += width;
        }
        canvas.drawRect(x, y, x + rowWidth, y + rowHeight, linePaint);

        // Отрисовка содержимого ячеек
        int cellX = x;
        paint.setTextAlign(Paint.Align.LEFT);
        int padding = 5;

        for (int i = 0; i < columnWidths.length; i++) {
            int cellWidth = columnWidths[i];
            if (isHeader) {
                paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
            } else {
                paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
            }

            drawMultilineText(canvas, paint, cellTexts[i], cellX + padding, y + 2 * padding, cellWidth - padding * 2);
            cellX += cellWidth;

            // Вертикальная линия
            canvas.drawLine(cellX, y, cellX, y + rowHeight, linePaint);
        }
    }

    private int calculateRowHeight(Paint paint, String[] texts, int[] columnWidths) {
        int maxHeight = 0;
        for (int i = 0; i < texts.length; i++) {
            String text = texts[i];
            int columnWidth = columnWidths[i];
            int lines = calculateTextLines(paint, text, columnWidth);
            maxHeight = Math.max(maxHeight, lines * (int) (paint.getTextSize() + 5));
        }
        return maxHeight + 10; // Добавляем отступ
    }

    private int calculateTextLines(Paint paint, String text, int maxWidth) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int lines = 1;

        for (String word : words) {
            if (paint.measureText(line + word) > maxWidth) {
                lines++;
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }

        return lines;
    }

    private void drawMultilineText(Canvas canvas, Paint paint, String text, float x, float y, float maxWidth) {
        StringBuilder line = new StringBuilder();
        float lineHeight = paint.getTextSize() + 5; // Высота строки с отступом
        float currentY = y;

        for (String word : text.split(" ")) {
            while (paint.measureText(word) > maxWidth) {
                // Разделяем слишком длинное слово на две части
                int cutIndex = paint.breakText(word, true, maxWidth, null);
                String part = word.substring(0, cutIndex);
                canvas.drawText(part, x, currentY, paint);
                currentY += lineHeight;
                word = word.substring(cutIndex); // Оставшаяся часть слова
            }

            String testLine = line + (line.length() > 0 ? " " : "") + word;
            if (paint.measureText(testLine) <= maxWidth) {
                line.append((line.length() > 0 ? " " : "")).append(word);
            } else {
                canvas.drawText(line.toString(), x, currentY, paint);
                line = new StringBuilder(word);
                currentY += lineHeight;
            }
        }

        // Отображаем оставшийся текст
        if (line.length() > 0) {
            canvas.drawText(line.toString(), x, currentY, paint);
        }
    }
}
