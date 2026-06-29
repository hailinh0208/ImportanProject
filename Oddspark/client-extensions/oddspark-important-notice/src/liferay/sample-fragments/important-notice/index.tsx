import { useState, useEffect, useRef, useCallback } from 'react';
import './ImportantNotice.css';
import { useImportantNotice, type Notice } from './useImportantNotice';
import configurations from '../configurations.json';

type ConfigKey = 'label' | 'labelColor' | 'titleColor' | 'contentColor' | 'bgColor';

const configEntry = configurations.find((c) => c.name === 'op-important-notice');
const defaults = Object.fromEntries(
    (configEntry?.fieldSets ?? []).flatMap((fs) => fs.fields).map((f) => [f.name, f.defaultValue])
) as Record<ConfigKey, string>;

interface ImportantNoticeProps {
    pagecode?: string;
    label?: string;
    labelColor?: string;
    titleColor?: string;
    contentColor?: string;
    bgColor?: string;
    notices?: Notice[];
    onNoticeClick?: (noticeId: number) => void;
}

function ImportantNotice({
    pagecode = '5',
    label = defaults.label,
    labelColor = defaults.labelColor,
    titleColor = defaults.titleColor,
    contentColor = defaults.contentColor,
    bgColor = defaults.bgColor,
    notices: noticesFromProps,
    onNoticeClick = () => {},
}: ImportantNoticeProps) {
    const { notices: noticesFromApi } = useImportantNotice(pagecode, noticesFromProps !== undefined);
    const notices = noticesFromProps ?? noticesFromApi;

    const [currentIndex, setCurrentIndex] = useState(0);
    const timerRef = useRef<ReturnType<typeof setInterval> | null>(null);
    const isMultiple = notices.length > 1;

    const goTo = useCallback(
        (index: number) => {
            setCurrentIndex(((index % notices.length) + notices.length) % notices.length);
        },
        [notices.length]
    );

    const stopCarousel = useCallback(() => {
        if (timerRef.current) {
            clearInterval(timerRef.current);
            timerRef.current = null;
        }
    }, []);

    const startCarousel = useCallback(() => {
        stopCarousel();
        timerRef.current = setInterval(() => {
            setCurrentIndex((prev) => (prev + 1) % notices.length);
        }, 5000);
    }, [notices.length, stopCarousel]);

    useEffect(() => {
        if (isMultiple) startCarousel();
        return () => stopCarousel();
    }, [isMultiple, startCarousel, stopCarousel]);

    if (!notices.length) return null;

    const current = notices[currentIndex];

    return (
        <div
            className="op-in-wrapper"
            style={{ backgroundColor: bgColor }}
            onMouseEnter={() => isMultiple && stopCarousel()}
            onMouseLeave={() => isMultiple && startCarousel()}
        >
            <div
                className="op-in-content"
                role="button"
                tabIndex={0}
                onClick={() => onNoticeClick(current.id)}
                onKeyDown={(e) => e.key === 'Enter' && onNoticeClick(current.id)}
            >
                <span className="op-in-icon">⚠</span>

                <span className="op-in-label" style={{ color: labelColor, borderColor: labelColor }}>
                    {label}
                </span>

                <div className="op-in-texts">
                    <span className="op-in-title" style={{ color: titleColor }}>
                        {current.noticeTitle}
                    </span>
                    {!isMultiple && current.noticeContent && (
                        <span className="op-in-body" style={{ color: contentColor }}>
                            {current.noticeContent.length > 100
                                ? current.noticeContent.slice(0, 100) + '...'
                                : current.noticeContent}
                        </span>
                    )}
                </div>

                <span className="op-in-chevron" style={{ color: titleColor }}>›</span>
            </div>

            {isMultiple && (
                <div className="op-in-controls" onClick={(e) => e.stopPropagation()}>
                    <div className="op-in-nav">
                        <button
                            className="op-in-nav-btn"
                            aria-label="Previous"
                            onClick={() => goTo(currentIndex - 1)}
                        >
                            ▲
                        </button>
                        <button
                            className="op-in-nav-btn"
                            aria-label="Next"
                            onClick={() => goTo(currentIndex + 1)}
                        >
                            ▼
                        </button>
                    </div>

                    <div className="op-in-dots">
                        {notices.map((_, i) => (
                            <button
                                key={i}
                                className={`op-in-dot ${i === currentIndex ? 'active' : ''}`}
                                aria-label={`Notice ${i + 1}`}
                                style={{
                                    backgroundColor:
                                        i === currentIndex ? labelColor : 'rgba(255,255,255,0.4)',
                                }}
                                onClick={() => goTo(i)}
                            />
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
}

export default ImportantNotice;
